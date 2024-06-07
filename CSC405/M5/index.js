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

let currentView = 'solid'; // solid, wireframe, textured
let useAlternateShader = false; // flag to toggle shaders
let texture; // texture variable

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
  gl.clearColor(0.2, 0.2, 0.2, 1.0); // Dark gray background
  // Clear the color buffer with specified clear color
  gl.clear(gl.COLOR_BUFFER_BIT);

  // Initialize shader programs
  const solidShaderProgram = initShaderProgram(gl, vsSource, fsSource);
  const wireframeShaderProgram = initShaderProgram(gl, vsSource, wireframeFsSource);
  const texturedShaderProgram = initShaderProgram(gl, texturedVsSource, texturedFsSource);

  let shaderProgram = solidShaderProgram;

  // Collect all the info needed to use the shader program.
  const programInfo = {
    program: shaderProgram,
    attribLocations: {
      vertexPosition: gl.getAttribLocation(shaderProgram, "aVertexPosition"),
      vertexColor: gl.getAttribLocation(shaderProgram, "aVertexColor"),
      textureCoord: gl.getAttribLocation(shaderProgram, "aTextureCoord"),
    },
    uniformLocations: {
      projectionMatrix: gl.getUniformLocation(shaderProgram, "uProjectionMatrix"),
      modelViewMatrix: gl.getUniformLocation(shaderProgram, "uModelViewMatrix"),
      uSampler: gl.getUniformLocation(shaderProgram, "uSampler"),
    },
  };

  // Initialize buffers
  const buffers = initBuffers(gl);

  // Load the texture
  texture = loadTexture(gl, 'texture.jpg');

  // Get the buttons from the DOM
  const rotateLeftButton = document.getElementById('rotateLeft');
  const rotateRightButton = document.getElementById('rotateRight');
  const rotateZButton = document.getElementById('rotateZ');
  const rotateYButton = document.getElementById('rotateY');
  const rotateXButton = document.getElementById('rotateX');
  const wireframeViewButton = document.getElementById('wireframeView');
  const solidViewButton = document.getElementById('solidView');
  const texturedViewButton = document.getElementById('texturedView');
  const toggleShaderButton = document.getElementById('toggleShader');

  const depthSlider = document.getElementById('depthSlider');
  const resetDepth = document.getElementById('resetDepth');

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
    far = event.target.value / 2;
    near = -event.target.value / 2;
  };

  resetDepth.onclick = function() {
    far = 100.0;
    near = 0.1;
  };

  wireframeViewButton.onclick = function() {
    currentView = 'wireframe';
    shaderProgram = wireframeShaderProgram;
    programInfo.program = shaderProgram;
    programInfo.attribLocations.vertexPosition = gl.getAttribLocation(shaderProgram, "aVertexPosition");
    programInfo.attribLocations.vertexColor = gl.getAttribLocation(shaderProgram, "aVertexColor");
    programInfo.attribLocations.textureCoord = null; // No texture coords for wireframe
    programInfo.uniformLocations.projectionMatrix = gl.getUniformLocation(shaderProgram, "uProjectionMatrix");
    programInfo.uniformLocations.modelViewMatrix = gl.getUniformLocation(shaderProgram, "uModelViewMatrix");
    programInfo.uniformLocations.uSampler = null;
  };

  solidViewButton.onclick = function() {
    currentView = 'solid';
    shaderProgram = solidShaderProgram;
    programInfo.program = shaderProgram;
    programInfo.attribLocations.vertexPosition = gl.getAttribLocation(shaderProgram, "aVertexPosition");
    programInfo.attribLocations.vertexColor = gl.getAttribLocation(shaderProgram, "aVertexColor");
    programInfo.attribLocations.textureCoord = null; // No texture coords for solid view
    programInfo.uniformLocations.projectionMatrix = gl.getUniformLocation(shaderProgram, "uProjectionMatrix");
    programInfo.uniformLocations.modelViewMatrix = gl.getUniformLocation(shaderProgram, "uModelViewMatrix");
    programInfo.uniformLocations.uSampler = null;
  };

  texturedViewButton.onclick = function() {
    currentView = 'textured';
    shaderProgram = texturedShaderProgram;
    programInfo.program = shaderProgram;
    programInfo.attribLocations.vertexPosition = gl.getAttribLocation(shaderProgram, "aVertexPosition");
    programInfo.attribLocations.vertexColor = null; // No vertex colors for textured view
    programInfo.attribLocations.textureCoord = gl.getAttribLocation(shaderProgram, "aTextureCoord");
    programInfo.uniformLocations.projectionMatrix = gl.getUniformLocation(shaderProgram, "uProjectionMatrix");
    programInfo.uniformLocations.modelViewMatrix = gl.getUniformLocation(shaderProgram, "uModelViewMatrix");
    programInfo.uniformLocations.uSampler = gl.getUniformLocation(shaderProgram, "uSampler");
  };

  toggleShaderButton.onclick = function() {
    useAlternateShader = !useAlternateShader;
  };

  let then = 0;

  // Draw the scene repeatedly
  function render(now) {
    now *= 0.001; // convert to seconds
    deltaTime = now - then;
    then = now;
    gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

    drawScene(gl, programInfo, buffers, cubeRotation, xyzRoation, currentView, useAlternateShader);
    cubeRotation += deltaTime * rotationDirection; // Update based on direction

    requestAnimationFrame(render);
  }
  requestAnimationFrame(render);
}

// Shader sources
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

const fsSource = `
  varying lowp vec4 vColor;

  void main(void) {
    gl_FragColor = vColor;
  }
`;

const wireframeFsSource = `
  varying lowp vec4 vColor;

  void main(void) {
    gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0); // Black color for wireframe
  }
`;

const texturedVsSource = `
  attribute vec4 aVertexPosition;
  attribute vec2 aTextureCoord;

  uniform mat4 uModelViewMatrix;
  uniform mat4 uProjectionMatrix;

  varying highp vec2 vTextureCoord;

  void main(void) {
    gl_Position = uProjectionMatrix * uModelViewMatrix * aVertexPosition;
    vTextureCoord = aTextureCoord;
  }
`;

const texturedFsSource = `
  varying highp vec2 vTextureCoord;

  uniform sampler2D uSampler;

  void main(void) {
    gl_FragColor = texture2D(uSampler, vTextureCoord);
  }
`;

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

function loadTexture(gl, url) {
  const texture = gl.createTexture();
  gl.bindTexture(gl.TEXTURE_2D, texture);

  // Because images have to be downloaded over the internet
  // they might take a moment until they are ready.
  // Until then, put a single pixel in the texture so we can
  // use it immediately. When the image has finished downloading
  // we'll update the texture with the contents of the image.
  const level = 0;
  const internalFormat = gl.RGBA;
  const width = 1;
  const height = 1;
  const border = 0;
  const srcFormat = gl.RGBA;
  const srcType = gl.UNSIGNED_BYTE;
  const pixel = new Uint8Array([0, 0, 255, 255]); // opaque blue
  gl.texImage2D(gl.TEXTURE_2D, level, internalFormat, width, height, border, srcFormat, srcType, pixel);

  const image = new Image();
  image.onload = function() {
    gl.bindTexture(gl.TEXTURE_2D, texture);
    gl.texImage2D(gl.TEXTURE_2D, level, internalFormat, srcFormat, srcType, image);

    // WebGL1 has different requirements for power of 2 images
    // vs. non power of 2 images so check if the image is a
    // power of 2 in both dimensions.
    if (isPowerOf2(image.width) && isPowerOf2(image.height)) {
      // Yes, it's a power of 2. Generate mips.
      gl.generateMipmap(gl.TEXTURE_2D);
    } else {
      // No, it's not a power of 2. Turn off mips and set
      // wrapping to clamp to edge
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_S, gl.CLAMP_TO_EDGE);
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_WRAP_T, gl.CLAMP_TO_EDGE);
      gl.texParameteri(gl.TEXTURE_2D, gl.TEXTURE_MIN_FILTER, gl.LINEAR);
    }
  };
  image.src = url;

  return texture;
}

function isPowerOf2(value) {
  return (value & (value - 1)) === 0;
}
main();