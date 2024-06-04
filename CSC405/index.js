let sphereRotation = 0.0;
let xyzRoation = [0, 0, 1];
let deltaTime = 0;
let rotationDirection = 1; 
let numTimesToSubdivide = 3;

let index = 0;

let positionsArray = [];
let normalsArray = [];

let near = 0.1;
let far = 100.0;
let radius = 1.5; // Distance of the camera from the origin
let theta = 0.1;
let phi = 0.0;
let dr = 5.0 * Math.PI/180.0;

let left = -3.0;
let right = 3.0;
//let top = 3.0;
let bottom = -3.0;

var va = vec4(0.0, 0.0, -1.0,1);
var vb = vec4(0.0, 0.942809, 0.333333, 1);
var vc = vec4(-0.816497, -0.471405, 0.333333, 1);
var vd = vec4(0.816497, -0.471405, 0.333333,1);

let lightPosition = vec4(1.0, 1.0, 1.0, 0.0);
let lightAmbient = vec4(0.2, 0.2, 0.2, 1.0);
let lightDiffuse = vec4(1.0, 1.0, 1.0, 1.0);
let lightSpecular = vec4(1.0, 1.0, 1.0, 1.0);

let materialAmbient = vec4(1.0, 0.0, 1.0, 1.0);
let materialDiffuse = vec4(1.0, 0.8, 0.0, 1.0);
let materialSpecular = vec4(1.0, 1.0, 1.0, 1.0);
let materialShininess = 20.0;

let ctm;
let ambientColor, diffuseColor, specularColor;

let modelViewMatrix, projectionMatrix;
let modelViewMatrixLoc, projectionMatrixLoc;
let eyeLoc;

let eye;
let at = vec3(0.0, 0.0, 0.0);
let up = vec3(0.0, 1.0, 0.0);

function triangle(a, b, c) {
  // normals are vectors
  normalsArray.push(vec4(a[0], a[1], a[2], 0.0));
  normalsArray.push(vec4(b[0], b[1], b[2], 0.0));
  normalsArray.push(vec4(c[0], c[1], c[2], 0.0));

  positionsArray.push(a);
  positionsArray.push(b);
  positionsArray.push(c);

  index += 3;
}

function divideTriangle(a, b, c, count) {
  if (count > 0) {
      const ab = normalize(mix(a, b, 0.5), true);
      const ac = normalize(mix(a, c, 0.5), true);
      const bc = normalize(mix(b, c, 0.5), true);

      divideTriangle(a, ab, ac, count - 1);
      divideTriangle(ab, b, bc, count - 1);
      divideTriangle(bc, c, ac, count - 1);
      divideTriangle(ab, bc, ac, count - 1);
  } else {
      triangle(a, b, c);
  }
}

function tetrahedron(a, b, c, d, n) {
  divideTriangle(a, b, c, n);
  divideTriangle(d, c, b, n);
  divideTriangle(a, d, b, n);
  divideTriangle(a, c, d, n);
}

main();

// start here
function main() {
  const canvas = document.querySelector("#glcanvas");
  const gl = canvas.getContext("webgl");

  // Only continue if WebGL is available and working
  if (!gl) {
    alert(
      "Unable to initialize WebGL. Your browser or machine may not support it."
    );
    return;
  }

  // Set clear color to black, fully opaque
  gl.clearColor(1.0, 1.0, 1.0, 1.0);

  gl.viewport(0, 0, canvas.width, canvas.height);
  // Clear the color buffer with specified clear color
  gl.clear(gl.COLOR_BUFFER_BIT);

  // vertex shader program
  const vsSource = `
  attribute vec4 aPosition;
  attribute vec4 aNormal;
  varying vec3 vN, vL, vE;
  
  uniform mat4 uModelViewMatrix;
  uniform mat4 uProjectionMatrix;
  uniform vec4 uLightPosition;
  uniform mat3 uNormalMatrix;
  
  void main() {
      vec3 pos = (uModelViewMatrix * aPosition).xyz;
      if (uLightPosition.w == 0.0) {
          vL = normalize(uLightPosition.xyz);
      } else {
          vL = normalize(uLightPosition.xyz - pos);
      }
      vE = -normalize(pos);
      vN = normalize(uNormalMatrix * aNormal.xyz);
      gl_Position = uProjectionMatrix * uModelViewMatrix * aPosition;
  }  
  `;

  // fragment shader program
  const fsSource = `
    precision mediump float;

    uniform vec4 uAmbientProduct;
    uniform vec4 uDiffuseProduct;
    uniform vec4 uSpecularProduct;
    uniform float uShininess;

    varying vec3 vN, vL, vE;
    
    void main() {
        vec3 H = normalize(vL + vE);
        vec4 ambient = uAmbientProduct;
    
        float Kd = max(dot(vL, vN), 0.0);
        vec4 diffuse = Kd * uDiffuseProduct;
    
        float Ks = pow(max(dot(vN, H), 0.0), uShininess);
        vec4 specular = Ks * uSpecularProduct;
    
        if (dot(vL, vN) < 0.0) {
            specular = vec4(0.0, 0.0, 0.0, 1.0);
        }
    
        gl_FragColor = ambient + diffuse + specular;
        gl_FragColor.a = 1.0;
    }  
  `;

  // Initialize a shader program; this is where all the lighting
  // for the vertices and so forth is established.
  const shaderProgram = initShaderProgram(gl, vsSource, fsSource);

  // tells WebGL to use our program when drawing
  gl.useProgram(shaderProgram);

  // Collect all the info needed to use the shader program.
  // Look up which attributes our shader program is using
  // for aVertexPosition, aVertexColor and also
  // look up uniform locations.
  const programInfo = {
    program: shaderProgram,
    attribLocations: {
      vertexPosition: gl.getAttribLocation(shaderProgram, "aPosition"),
      vertexNormal: gl.getAttribLocation(shaderProgram, "aNormal"),
    },
    uniformLocations: {
      projectionMatrix: gl.getUniformLocation(shaderProgram, "uProjectionMatrix"),
      modelViewMatrix: gl.getUniformLocation(shaderProgram, "uModelViewMatrix"),
      normalMatrix: gl.getUniformLocation(shaderProgram, "uNormalMatrix"),
      ambientProduct: gl.getUniformLocation(shaderProgram, "uAmbientProduct"),
      diffuseProduct: gl.getUniformLocation(shaderProgram, "uDiffuseProduct"),
      specularProduct: gl.getUniformLocation(shaderProgram, "uSpecularProduct"),
      shininess: gl.getUniformLocation(shaderProgram, "uShininess"),
      lightPosition: gl.getUniformLocation(shaderProgram, "uLightPosition")
    }
  };

  
   ambientProduct = mult(lightAmbient, materialAmbient);
   diffuseProduct = mult(lightDiffuse, materialDiffuse);
   specularProduct = mult(lightSpecular, materialSpecular);

   
  // Here's where we call the routine that builds all the
  // objects we'll be drawing.
  const buffers = initBuffers(gl);

  // Get the buttons from the DOM &  Event listeners for changing
  document.getElementById('increaseR').onclick = function(){radius *= 2.0;};
  document.getElementById('decreaseR').onclick = function(){radius *= 0.5;};
  document.getElementById('increaseTheta').onclick = function(){theta += dr;};
  document.getElementById('decreaseTheta').onclick = function(){theta -= dr;};
  document.getElementById('increasePhi').onclick = function(){phi += dr;};
  document.getElementById('decreasePhi').onclick = function(){phi -= dr;};
  document.getElementById('increaseSub').onclick = function()
  {
    numTimesToSubdivide++;
    index = 0;
    positionsArray = [];
    normalsArray = [];
    const buffers = initBuffers(gl);  };
  document.getElementById('decreaseSub').onclick = function()
  {
    if(numTimesToSubdivide) numTimesToSubdivide--;
    index = 0;
    positionsArray = [];
    normalsArray = [];    
    const buffers = initBuffers(gl);
  };

  let then = 0;

  // Draw the scene repeatedly
  function render(now) {
    now *= 0.001; // convert to seconds
    deltaTime = now - then;
    then = now;
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    drawScene(gl, programInfo, buffers, sphereRotation, xyzRoation);
    sphereRotation += deltaTime * rotationDirection; // Update based on direction

    requestAnimationFrame(render);
  }
  requestAnimationFrame(render);
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