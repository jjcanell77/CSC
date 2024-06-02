var cubeRotation = 0.0;
var deltaTime = 0;
var radius = 4.0;
var theta = 0;
var phi = 0;
var near = 0.3;
var far = 3.0;
var left = -5.0;
var right = 5.0;
var bottom = -5.0;
var ytop = 5.0;
var fovy = 45.0;
var aspect = 1.0;

const at = vec3.fromValues(0.0, 0.0, 0.0);
const up = vec3.fromValues(0.0, 1.0, 0.0);

const vertices = [
    vec4.fromValues(-0.5, -0.5,  1.5, 1.0),
    vec4.fromValues(-0.5,  0.5,  1.5, 1.0),
    vec4.fromValues(0.5,  0.5,  1.5, 1.0),
    vec4.fromValues(0.5, -0.5,  1.5, 1.0),
    vec4.fromValues(-0.5, -0.5, 0.5, 1.0),
    vec4.fromValues(-0.5,  0.5, 0.5, 1.0),
    vec4.fromValues(0.5,  0.5, 0.5, 1.0),
    vec4.fromValues(0.5, -0.5, 0.5, 1.0)
];

const vertexColors = [
    vec4.fromValues(0.0, 0.0, 0.0, 1.0),  // black
    vec4.fromValues(1.0, 0.0, 0.0, 1.0),  // red
    vec4.fromValues(1.0, 1.0, 0.0, 1.0),  // yellow
    vec4.fromValues(0.0, 1.0, 0.0, 1.0),  // green
    vec4.fromValues(0.0, 0.0, 1.0, 1.0),  // blue
    vec4.fromValues(1.0, 0.0, 1.0, 1.0),  // magenta
    vec4.fromValues(0.0, 1.0, 1.0, 1.0),  // cyan
    vec4.fromValues(1.0, 1.0, 1.0, 1.0)   // white
];

let pointsArray = [];
let colorsArray = [];

function quad(a, b, c, d) {
    pointsArray.push(...vertices[a]);
    colorsArray.push(...vertexColors[a]);
    pointsArray.push(...vertices[b]);
    colorsArray.push(...vertexColors[a]);
    pointsArray.push(...vertices[c]);
    colorsArray.push(...vertexColors[a]);
    pointsArray.push(...vertices[a]);
    colorsArray.push(...vertexColors[a]);
    pointsArray.push(...vertices[c]);
    colorsArray.push(...vertexColors[a]);
    pointsArray.push(...vertices[d]);
    colorsArray.push(...vertexColors[a]);
}

function colorCube() {
    quad(1, 0, 3, 2);
    quad(2, 3, 7, 6);
    quad(3, 0, 4, 7);
    quad(6, 5, 1, 2);
    quad(4, 5, 6, 7);
    quad(5, 4, 0, 1);
}

function initBuffers(gl){
    colorCube();
    const positionBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(pointsArray), gl.STATIC_DRAW);

    const colorBuffer = gl.createBuffer();
    gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
    gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colorsArray), gl.STATIC_DRAW);

    return {
        position: positionBuffer,
        color: colorBuffer
    };
}

function drawScene(gl, programInfo, buffers){
  gl.clearColor(0.0, 0.0, 0.0, 1.0);
  gl.clearDepth(1.0);
  gl.enable(gl.DEPTH_TEST);
  gl.depthFunc(gl.LEQUAL);
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);


  const eye = vec3.fromValues(
    radius * Math.sin(theta) * Math.cos(phi),
    radius * Math.sin(theta) * Math.sin(phi),
    radius * Math.cos(theta)
  );

  const modelViewMatrix = mat4.create();
  mat4.lookAt(modelViewMatrix, eye, at, up);

  const fieldOfView = (fovy * Math.PI) / 180;
  const projectionMatrix = mat4.create();
  mat4.perspective(projectionMatrix, fieldOfView, aspect, near, far);

  gl.useProgram(programInfo.program);
  gl.uniformMatrix4fv(programInfo.uniformLocations.modelViewMatrix, false, modelViewMatrix);
  gl.uniformMatrix4fv(programInfo.uniformLocations.projectionMatrix, false, projectionMatrix);

  gl.useProgram(programInfo.program);

  gl.uniformMatrix4fv(programInfo.uniformLocations.projectionMatrix, false, projectionMatrix);
  gl.uniformMatrix4fv(programInfo.uniformLocations.modelViewMatrix, false, modelViewMatrix);

  {
    const numComponents = 4;
    const type = gl.FLOAT;
    const normalize = false;
    const stride = 0;
    const offset = 0;
    gl.bindBuffer(gl.ARRAY_BUFFER, buffers.position);
    gl.vertexAttribPointer(programInfo.attribLocations.vertexPosition, numComponents, type, normalize, stride, offset);
    gl.enableVertexAttribArray(programInfo.attribLocations.vertexPosition);
  }

  {
    const numComponents = 4;
    const type = gl.FLOAT;
    const normalize = false;
    const stride = 0;
    const offset = 0;
    gl.bindBuffer(gl.ARRAY_BUFFER, buffers.color);
    gl.vertexAttribPointer(programInfo.attribLocations.vertexColor, numComponents, type, normalize, stride, offset);
    gl.enableVertexAttribArray(programInfo.attribLocations.vertexColor);
  }

  {
    const vertexCount = 36;
    const type = gl.UNSIGNED_SHORT;
    const offset = 0;
    gl.drawArrays(gl.TRIANGLES, 0, vertexCount);
  }
}

function main(){
  const canvas = document.querySelector("#webgl");
  const gl = canvas.getContext("webgl");

  if (gl === null) {
    alert("Unable to initialize WebGL. Your browser or machine may not support it.");
    return;
  }

  gl.clearColor(0.0, 0.0, 0.0, 1.0);
  gl.clear(gl.COLOR_BUFFER_BIT);

  const vsSource = `
    attribute vec4 vPosition;
    attribute vec4 vColor;
    varying vec4 fColor;
    uniform mat4 modelViewMatrix;
    uniform mat4 projectionMatrix;
    void main(void) {
      gl_Position = projectionMatrix * modelViewMatrix * vPosition;
      fColor = vColor;
    }
  `;

  const fsSource = `
    #ifdef GL_ES
    precision highp float;
    #endif
    varying vec4 fColor;
    void main(void) {
      gl_FragColor = fColor;
    }
  `;

  const shaderProgram = initShaderProgram(gl, vsSource, fsSource);

  const programInfo = {
    program: shaderProgram,
    attribLocations: {
      vertexPosition: gl.getAttribLocation(shaderProgram, "vPosition"),
      vertexColor: gl.getAttribLocation(shaderProgram, "vColor"),
    },
    uniformLocations: {
      projectionMatrix: gl.getUniformLocation(shaderProgram, "projectionMatrix"),
      modelViewMatrix: gl.getUniformLocation(shaderProgram, "modelViewMatrix"),
    },
  };

  const buffers = initBuffers(gl);

  document.getElementById("fovSlider").onchange = function(event) {
    fovy = event.target.value;
};

  let then = 0;

  function render(now){
    now *= 0.001;
    deltaTime = now - then;
    then = now;
    gl.clear( gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    drawScene(gl, programInfo, buffers);

    requestAnimationFrame(render);
  }
  requestAnimationFrame(render);
}

function initShaderProgram(gl, vsSource, fsSource){
  const vertexShader = loadShader(gl, gl.VERTEX_SHADER, vsSource);
  const fragmentShader = loadShader(gl, gl.FRAGMENT_SHADER, fsSource);

  const shaderProgram = gl.createProgram();
  gl.attachShader(shaderProgram, vertexShader);
  gl.attachShader(shaderProgram, fragmentShader);
  gl.linkProgram(shaderProgram);

  if (!gl.getProgramParameter(shaderProgram, gl.LINK_STATUS)) {
    alert(`Unable to initialize the shader program: ${gl.getProgramInfoLog(shaderProgram)}`);
    return null;
  }

  return shaderProgram;
}

function loadShader(gl, type, source){
  const shader = gl.createShader(type);
  gl.shaderSource(shader, source);
  gl.compileShader(shader);

  if (!gl.getShaderParameter(shader, gl.COMPILE_STATUS)) {
    alert(`An error occurred compiling the shaders: ${gl.getShaderInfoLog(shader)}`);
    gl.deleteShader(shader);
    return null;
  }

  return shader;
}

main();