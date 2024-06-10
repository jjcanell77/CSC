"use strict";

let canvas;
let gl;

let positions = [];
let colors = [];
let numTimesToSubdivide = 3;

window.onload = function init()
{
     canvas = document.getElementById("glcanvas");
     gl = canvas.getContext('webgl2');
     if (!gl) alert("WebGL 2.0 isn't available");

     // First, initialize the vertices of our 3D gasket
     // Four vertices on unit circle
     // Intial tetrahedron with equal length sides

     let vertices = [
          vec3(0.0000,  0.0000, -1.0000),
          vec3(0.0000,  0.9428,  0.3333),
          vec3(-0.8165, -0.4714,  0.3333),
          vec3(0.8165, -0.4714,  0.3333)
     ];

     divideTetra(vertices[0], vertices[1], vertices[2], vertices[3], numTimesToSubdivide);

     // Sort triangles by their depth (Painter's Algorithm)
     paintersAlgorithm();

     //  Configure WebGL
     gl.viewport(0, 0, canvas.width, canvas.height);
     gl.clearColor(1.0, 1.0, 1.0, 1.0);

     // disable hidden-surface removal for Painter’s algorithm 
     gl.disable(gl.DEPTH_TEST);
     // enable hidden-surface removal for Z-buffer algorithm 
     // gl.enable(gl.DEPTH_TEST);

     //  Load shaders and initialize attribute buffers
     const vsSource = `#version 300 es
          in vec3 aPosition;
          in vec3 aColor;
          out vec4 vColor;

          void
          main(){
               gl_Position = vec4(aPosition, 1.0);
               vColor = vec4(aColor, 1.0);
          }
     `;

     const fsSource = `#version 300 es
          precision mediump float;

          in vec4 vColor;
          out vec4 fColor;

          void
          main(){
               fColor = vColor;
          }
     `;
     let program = initShaderProgram(gl, vsSource, fsSource);
     gl.useProgram(program);

     // Create a buffer object, initialize it, and associate it with the
     //  associated attribute variable in our vertex shader

     let cBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, cBuffer);
     gl.bufferData(gl.ARRAY_BUFFER, flatten(colors), gl.STATIC_DRAW);

     let colorLoc = gl.getAttribLocation(program, "aColor");
     gl.vertexAttribPointer(colorLoc, 3, gl.FLOAT, false, 0, 0);
     gl.enableVertexAttribArray(colorLoc);

     let vBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, vBuffer);
     gl.bufferData(gl.ARRAY_BUFFER, flatten(positions), gl.STATIC_DRAW);

     let positionLoc = gl.getAttribLocation(program, "aPosition");
     gl.vertexAttribPointer(positionLoc, 3, gl.FLOAT, false, 0, 0);
     gl.enableVertexAttribArray(positionLoc);

     render();
};

function triangle( a, b, c, color )
{

    // add colors and vertices for one triangle

    let baseColors = [
        vec3(1.0, 0.0, 0.0),
        vec3(0.0, 1.0, 0.0),
        vec3(0.0, 0.0, 1.0),
        vec3(0.0, 0.0, 0.0)
    ];

    colors.push(baseColors[color]);
    positions.push(a);
    colors.push(baseColors[color]);
    positions.push(b);
    colors.push(baseColors[color]);
    positions.push(c);
}

function tetra( a, b, c, d )
{
    // tetrahedron with each side using
    // a different color

    triangle(a, c, b, 0);
    triangle(a, c, d, 1);
    triangle(a, b, d, 2);
    triangle(b, c, d, 3);
}

function divideTetra(a, b, c, d, count)
{
    // check for end of recursion

    if (count === 0) {
        tetra(a, b, c, d);
    }

    // find midpoints of sides
    // divide four smaller tetrahedra

    else {
        var ab = mix(a, b, 0.5);
        var ac = mix(a, c, 0.5);
        var ad = mix(a, d, 0.5);
        var bc = mix(b, c, 0.5);
        var bd = mix(b, d, 0.5);
        var cd = mix(c, d, 0.5);

        --count;

        divideTetra(a, ab, ac, ad, count);
        divideTetra(ab,  b, bc, bd, count);
        divideTetra(ac, bc,  c, cd, count);
        divideTetra(ad, bd, cd,  d, count);
    }
}

function paintersAlgorithm() {
     // calculates the average depth of each triangle
     let depthData = [];
     for (let i = 0; i < positions.length; i += 3) {
          let a = positions[i];
          let b = positions[i + 1];
          let c = positions[i + 2];
          let depth = (a[2] + b[2] + c[2]) / 3;
          depthData.push(
               { depth: depth, index: i }
          );
     }
 
     // sorts the triangles by  furthest depth to nearest
     depthData.sort((a, b) => b.depth - a.depth);
 
     // reorders positions and colors arrays based on sorted depth
     let sortedPositions = [];
     let sortedColors = [];
     for (let j = 0; j < depthData.length; j++) {
          let idx = depthData[j].index;
          sortedPositions.push(
               positions[idx], positions[idx + 1], positions[idx + 2]
          );
          sortedColors.push(
               colors[idx], colors[idx + 1], colors[idx + 2]
          );
     }
 
     positions = sortedPositions;
     colors = sortedColors;
 }


function render()
{
     // clear buffer hidden-surface removal for Z-buffer algorithm 
     gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);
     gl.drawArrays(gl.TRIANGLES, 0, positions.length);
}

// initializes a shader program, so WebGL knows how to draw our data
function initShaderProgram(gl, vsSource, fsSource) {
     const vertexShader = loadShader(gl, gl.VERTEX_SHADER, vsSource);
     const fragmentShader = loadShader(gl, gl.FRAGMENT_SHADER, fsSource);

     // Create the shader program
     const shaderProgram = gl.createProgram();
     gl.attachShader(shaderProgram, vertexShader);
     gl.attachShader(shaderProgram, fragmentShader);
     gl.linkProgram(shaderProgram);

     // checks if failed
     if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
          alert(
          `Unable to initialize the shader program: ${gl.getProgramInfoLog(
               shaderProgram
          )}`
          );
          return null;
     }

     return shaderProgram;
}
   
// creates a shader of the given type, uploads the source and compiles it.
function loadShader(gl, type, source) {
     const shader = gl.createShader(type);

     // Send the source to the shader object
     gl.shaderSource(shader, source);

     // Compile the shader program
     gl.compileShader(shader);

     // See if it compiled successfully
     if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
          alert(
               `An error occurred compiling the shaders: ${gl.getShaderInfoLog(shader)}`
          );
          gl.deleteShader(shader);
          return null;
     }

     return shader;
}