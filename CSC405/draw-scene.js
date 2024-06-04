function drawScene(gl, programInfo, buffers, sphereRotation, xyzRoation) {
  // Clear the canvas before we start drawing on it.
  gl.enable(gl.DEPTH_TEST); // Enable depth testing
 
  // set up the camera from the eye
  eye = [
    radius * Math.sin(theta) * Math.cos(phi),
    radius * Math.sin(theta) * Math.sin(phi),
    radius * Math.cos(theta)
  ];
  // sets the drawing position to the "identity" point, which is the center of the scene.
  modelViewMatrix = mat4.create();
  mat4.lookAt(modelViewMatrix, eye, at, up);

  const fieldOfView = (45 * Math.PI) / 180;
  const aspect = gl.canvas.clientWidth / gl.canvas.clientHeight;

  // as the destination to receive the result.
  projectionMatrix = mat4.create();
  mat4.perspective(projectionMatrix, fieldOfView, aspect, near, far);

  // start drawing the square.
  mat4.translate(
    modelViewMatrix,  // destination matrix
    modelViewMatrix,  // matrix to translate
    [-0.0, 0.0, -5.0] // amount to translate

  ); 
  mat4.rotate(
    modelViewMatrix, // destination matrix
    modelViewMatrix, // matrix to rotate
    sphereRotation, // amount to rotate in radians
    xyzRoation     // axis to rotate around
  ); 

  // buffer into the vertexPosition attribute.
  setPositionAttribute(gl, buffers, programInfo);
  setNormalAttribute(gl, buffers, programInfo);

  // tells WebGL which indices to use to index the vertices
  gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, buffers.indices);

  // tells WebGL to use our program when drawing
  // gl.useProgram(programInfo.program);

  // sets the shader uniforms
  gl.uniformMatrix4fv(programInfo.uniformLocations.projectionMatrix, false, projectionMatrix);
  gl.uniformMatrix4fv(programInfo.uniformLocations.modelViewMatrix, false, modelViewMatrix);
  gl.uniform4fv(programInfo.uniformLocations.ambientProduct, flatten(ambientProduct));
  gl.uniform4fv(programInfo.uniformLocations.diffuseProduct, flatten(diffuseProduct));
  gl.uniform4fv(programInfo.uniformLocations.specularProduct, flatten(specularProduct));
  gl.uniform1f(programInfo.uniformLocations.shininess, materialShininess);
  gl.uniform4fv(programInfo.uniformLocations.lightPosition, flatten(lightPosition));

  const normalMatrix = mat3.create();
  mat3.normalFromMat4(normalMatrix, modelViewMatrix);
  gl.uniformMatrix3fv(programInfo.uniformLocations.normalMatrix, false, normalMatrix);

  console.log(numTimesToSubdivide)

  for( let i=0; i<index; i+=3)
    gl.drawArrays( gl.TRIANGLES, i, 3 );
 // Draws the sphere
//  {
//     const vertexCount = index; // Use the total number of indices
//     const type = gl.UNSIGNED_SHORT;
//     const offset = 0;
//     gl.drawElements(gl.TRIANGLES, vertexCount, type, offset);
//   }
}

// Tell WebGL how to pull out the positions from the position
// buffer into the vertexPosition attribute.
function setPositionAttribute(gl, buffers, programInfo) {
  // Bind the position buffer.
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.position);

  // Get the location of the vertexPosition attribute from the shader program.
  const positionLoc = gl.getAttribLocation(programInfo.program, "aPosition");

  // Specify how to pull out the data from the position buffer.
  gl.vertexAttribPointer(positionLoc, 4, gl.FLOAT, false, 0, 0);

  // Enable the vertex position attribute array.
  gl.enableVertexAttribArray(positionLoc);
}


// Tell WebGL how to pull out the colors from the color buffer
// into the vertexColor attribute.
function setNormalAttribute(gl, buffers, programInfo) {
  gl.bindBuffer(gl.ARRAY_BUFFER, buffers.normal);
  const normalLoc = gl.getAttribLocation(programInfo.program, "aNormal");
  gl.vertexAttribPointer(normalLoc, 4, gl.FLOAT, false, 0, 0);
  gl.enableVertexAttribArray(normalLoc);
}