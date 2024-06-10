function drawScene(gl, programInfo, buffers, cubeRotation,currentView) {
  // Clear the canvas before we start drawing on it.
  gl.enable(gl.DEPTH_TEST); // Enable depth testing

  // Set the background color to something other than black
  gl.clearColor(0.2, 0.2, 0.2, 1.0); // Dark gray background
  gl.clear(gl.COLOR_BUFFER_BIT | gl.DEPTH_BUFFER_BIT);

  // set up the camera from the eye
  const eye = [
    radius * Math.sin(theta) * Math.cos(phi),
    radius * Math.sin(theta) * Math.sin(phi),
    radius * Math.cos(theta)
  ];

  // sets the drawing position to the "identity" point, which is the center of the scene.
  const modelViewMatrix = mat4.create();
  mat4.lookAt(modelViewMatrix, eye, at, up);

  const fieldOfView = (45 * Math.PI) / 180;
  const aspect = gl.canvas.clientWidth / gl.canvas.clientHeight;

  // as the destination to receive the result.
  const projectionMatrix = mat4.create();
  mat4.perspective(projectionMatrix, fieldOfView, aspect, near, far);

  // start drawing the square.
  mat4.translate(
    modelViewMatrix,  // destination matrix
    modelViewMatrix,  // matrix to translate
    [-0.0, 0.0, -5.0] // amount to translate
  ); 
  if(zRotation){
    mat4.rotate(
      modelViewMatrix, // destination matrix
      modelViewMatrix, // matrix to rotate
      cubeRotation, // amount to rotate in radians
      [0, 0, 1]     // axis to rotate around
    ); 
  }
  if(yRotation){
    mat4.rotate(
      modelViewMatrix, // destination matrix
      modelViewMatrix, // matrix to rotate
      cubeRotation, // amount to rotate in radians
      [1, 0, 0]     // axis to rotate around
    ); 
  }
  if(xRotation){
    mat4.rotate(
      modelViewMatrix, // destination matrix
      modelViewMatrix, // matrix to rotate
      cubeRotation, // amount to rotate in radians
      [1, 0, 0]     // axis to rotate around
    ); 
  }
  
  // tells WebGL to use our program when drawing
  gl.useProgram(programInfo.program);

  // buffer into the vertexPosition attribute.
  setPositionAttribute(gl, buffers, programInfo);

  // tells WebGL which indices to use to index the vertices
  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buffers.indices);

  // sets the shader uniforms
  gl.uniformMatrix4fv(
    programInfo.uniformLocations.projectionMatrix,
    false,
    projectionMatrix
  );
  gl.uniformMatrix4fv(
    programInfo.uniformLocations.modelViewMatrix,
    false,
    modelViewMatrix
  );

  if (currentView === 'textured') {
    gl.activeTexture(gl.TEXTURE0);
    gl.bindTexture(gl.TEXTURE_2D, texture);
    gl.uniform1i(programInfo.uniformLocations.uSampler, 0);
    setTextureAttribute(gl, buffers, programInfo);
  }else{
    setColorAttribute(gl, buffers, programInfo);
  }

  // draws the square
  {
    let vertexCount = 36;
    let type = gl.UNSIGNED_SHORT;
    let offset = 0;

    if (currentView === 'wireframe') {
      // Draw wireframe view
      for (let i = 0; i < vertexCount; i += 3) {
        gl.drawElements(gl.LINE_LOOP, 3, type, i * 2);
      }
    } else {
      // Draw solid or textured view
      gl.drawElements(gl.TRIANGLES, vertexCount, type, offset);
    }
  }
}

// Tell WebGL how to pull out the positions from the position
// buffer into the vertexPosition attribute.
function setPositionAttribute(gl, buffers, programInfo) {
  const numComponents = 3;
  const type = gl.FLOAT; // the data in the buffer is 32bit floats
  const normalize = false; // don't normalize
  const stride = 0; // how many bytes to get from one set of values to the next
  // 0 = use type and numComponents above
  const offset = 0; // how many bytes inside the buffer to start from
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.position);
  gl.vertexAttribPointer(
    programInfo.attribLocations.vertexPosition,
    numComponents,
    type,
    normalize,
    stride,
    offset
  );
  gl.enableVertexAttribArray(programInfo.attribLocations.vertexPosition);
}

// Tell WebGL how to pull out the colors from the color buffer
// into the vertexColor attribute.
function setColorAttribute(gl, buffers, programInfo) {
  const numComponents = 4;
  const type = gl.FLOAT;
  const normalize = false;
  const stride = 0;
  const offset = 0;
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.color);
  gl.vertexAttribPointer(
    programInfo.attribLocations.vertexColor,
    numComponents,
    type,
    normalize,
    stride,
    offset
  );
  gl.enableVertexAttribArray(programInfo.attribLocations.vertexColor);
}

// Tell WebGL how to pull out the texture coordinates from the buffer
// into the textureCoord attribute.
function setTextureAttribute(gl, buffers, programInfo) {
  const numComponents = 2; // because texture coordinates are 2D
  const type = gl.FLOAT;
  const normalize = false;
  const stride = 0;
  const offset = 0;
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.textureCoord);
  gl.vertexAttribPointer(
    programInfo.attribLocations.textureCoord,
    numComponents,
    type,
    normalize,
    stride,
    offset
  );
  gl.enableVertexAttribArray(programInfo.attribLocations.textureCoord);
}