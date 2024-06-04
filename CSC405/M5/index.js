let cubeRotation = 0.0;
let xyzRoation = [0, 0, 1];
let deltaTime = 0;
let rotationDirection = 1; 
let near = 0.1;
let far = 100.0;
let radius = 6.0; // Distance of the camera from the origin
let theta = 0; // Initial theta value
let phi = 0; // Initial phi value
const at = [0.0, 0.0, 0.0]; // Look-at point
const up = [0.0, 1.0, 0.0]; // Up vector

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
  gl.clearColor(0.0, 0.0, 0.0, 1.0);
  // Clear the color buffer with specified clear color
  gl.clear(gl.COLOR_BUFFER_BIT);

  // vertex shader program
  const vsSource = `
    attribute vec4 aVertexPosition;
    attribute vec4 aVertexColor;

    uniform mat4 uModelViewMatrix;
    uniform mat4 uProjectionMatrix;

    varying lowp vec4 vColor;

    void main(void) {
      gl_Position = uProjectionMatrix * uModelViewMatrix * aVertexPosition;
      vColor = aVertexColor;
    }
  `;

  // fragment shader program
  const fsSource = `
    varying lowp vec4 vColor;

    void main(void) {
      gl_FragColor = vColor;
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
      vertexPosition: gl.getAttribLocation(shaderProgram, "aVertexPosition"),
      vertexColor: gl.getAttribLocation(shaderProgram, "aVertexColor"),
    },
    uniformLocations: {
      projectionMatrix: gl.getUniformLocation(shaderProgram, "uProjectionMatrix"),
      modelViewMatrix: gl.getUniformLocation(shaderProgram, "uModelViewMatrix"),
    },
  };


  // Here's where we call the routine that builds all the
  // objects we'll be drawing.
  const buffers = initBuffers(gl);

  // Get the buttons from the DOM
  const rotateLeftButton = document.getElementById('rotateLeft');
  const rotateRightButton = document.getElementById('rotateRight');
  const rotateZButton = document.getElementById('rotateZ');
  const rotateYButton = document.getElementById('rotateY');
  const rotateXButton = document.getElementById('rotateX');

  const depthSlider = document.getElementById('depthSlider')
  const resetDepth = document.getElementById('resetDepth')

  // Event listeners for changing the rotation direction
  rotateLeftButton.onclick = function() {
    rotationDirection = 1; // Counterclockwise
  };
  rotateRightButton.onclick = function() {
    rotationDirection = -1; // Clockwise
  };
  rotateZButton.onclick = function() {
    xyzRoation = [0, 0, 1];
  };
  rotateYButton.onclick = function() {
    xyzRoation = [0, 1, 0];
  };
  rotateXButton.onclick = function() {
    xyzRoation = [1, 0, 0];
  };
  depthSlider.onchange = function(event) {
    far = event.target.value/2;
    near = -event.target.value/2
  };

  resetDepth.onclick = function() {
    far = 100.0;
    near = 0.1;
  };

  let then = 0;

  // Draw the scene repeatedly
  function render(now) {
    now *= 0.001; // convert to seconds
    deltaTime = now - then;
    then = now;
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    drawScene(gl, programInfo, buffers, cubeRotation, xyzRoation);
    cubeRotation += deltaTime * rotationDirection; // Update based on direction

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