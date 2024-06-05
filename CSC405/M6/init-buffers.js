function initBuffers() {
     positionsArray = [];
     index = 0;
     tetrahedron(va, vb, vc, vd, numTimesToSubdivide);

     const positionBuffer = initPositionBuffer();
   
     return {
       position: positionBuffer,
     };
   }
   

function initPositionBuffer() {
     // creates a buffer for the sphere's positions storing data such as vertices or colors.
     const positionBuffer = gl.createBuffer();

     // binds a given WebGLBuffer to a target.
     gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);

     // now pass the list of positions into WebGL to build the shape.
     gl.bufferData(gl.ARRAY_BUFFER, flatten(positionsArray), gl.STATIC_DRAW);

     return positionBuffer;
}