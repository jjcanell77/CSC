function drawScene( programInfo, buffers, sphereRotation, xyzRotation) {
  // Clear the canvas before we start drawing on it.
  gl.enable(gl.DEPTH_TEST); // Enable depth testing
 
  // set up the camera from the eye
  eye = vec3(radius*Math.sin(theta)*Math.cos(phi),
        radius*Math.sin(theta)*Math.sin(phi), radius*Math.cos(theta));
  // sets the drawing position to the "identity" point, which is the center of the scene.
  modelViewMatrix = lookAt(eye, at , up);

  // as the destination to receive the result.
  projectionMatrix = ortho(left, right, bottom, theTop, near, far);

  // start drawing the square.
  modelViewMatrix = mult( modelViewMatrix, rotate(sphereRotation, xyzRotation));

  // buffer into the vertexPosition attribute.
  setPositionAttribute(gl, buffers, programInfo);

  // sets the shader uniforms
  gl.uniformMatrix4fv(programInfo.uniformLocations.projectionMatrix, false, flatten(projectionMatrix));
  gl.uniformMatrix4fv(programInfo.uniformLocations.modelViewMatrix, false, flatten(modelViewMatrix));

  for (let i = 0; i < index; i += 3) {
    gl.drawArrays(gl.LINE_LOOP, i, 3);
  }
}

// Tell WebGL how to pull out the positions from the position
// buffer into the vertexPosition attribute.
function setPositionAttribute(gl, buffers, programInfo) {
  // Bind the position buffer.
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.position);

  // Get the location of the vertexPosition attribute from the shader program.
  const positionLoc = programInfo.attribLocations.vertexPosition;

  // Specify how to pull out the data from the position buffer.
  gl.vertexAttribPointer(positionLoc, 4, gl.FLOAT, false, 0, 0);

  // Enable the vertex position attribute array.
  gl.enableVertexAttribArray(positionLoc);
}