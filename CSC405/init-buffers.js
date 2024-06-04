function initBuffers(gl) {
     tetrahedron(va, vb, vc, vd, numTimesToSubdivide);

     const positionBuffer = initPositionBuffer(gl);
     const normalBuffer = initNormalBuffer(gl);
     const indexBuffer = initIndexBuffer(gl);
   
     return {
       position: positionBuffer,
       normal: normalBuffer,
       indices: indexBuffer,
     };
   }
   

function initPositionBuffer(gl) {
     // creates a buffer for the sphere's positions storing data such as vertices or colors.
     const positionBuffer = gl.createBuffer();

     // binds a given WebGLBuffer to a target.
     gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);

     // now pass the list of positions into WebGL to build the shape.
     gl.bufferData(gl.ARRAY_BUFFER, flatten(positionsArray), gl.STATIC_DRAW);

     return positionBuffer;
}

function initNormalBuffer(gl) {
     const normalBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, normalBuffer);
     gl.bufferData(gl.ARRAY_BUFFER, flatten(normalsArray), gl.STATIC_DRAW);
     return normalBuffer;
}
   

function initIndexBuffer(gl) {
     const indexBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indexBuffer);
     // Now send the element array to GL
     gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array([...Array(index).keys()]), gl.STATIC_DRAW);
     return indexBuffer;
} 