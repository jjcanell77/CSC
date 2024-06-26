function initBuffers(gl) {
     // Position buffer setup
     const positionBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, positionBuffer);
   
     const positions = [
       // Front face
       -1.0, -1.0,  1.0,
        1.0, -1.0,  1.0,
        1.0,  1.0,  1.0,
       -1.0,  1.0,  1.0,
       // Back face
       -1.0, -1.0, -1.0,
       -1.0,  1.0, -1.0,
        1.0,  1.0, -1.0,
        1.0, -1.0, -1.0,
       // Top face
       -1.0,  1.0, -1.0,
       -1.0,  1.0,  1.0,
        1.0,  1.0,  1.0,
        1.0,  1.0, -1.0,
       // Bottom face
       -1.0, -1.0, -1.0,
        1.0, -1.0, -1.0,
        1.0, -1.0,  1.0,
       -1.0, -1.0,  1.0,
       // Right face
        1.0, -1.0, -1.0,
        1.0,  1.0, -1.0,
        1.0,  1.0,  1.0,
        1.0, -1.0,  1.0,
       // Left face
       -1.0, -1.0, -1.0,
       -1.0, -1.0,  1.0,
       -1.0,  1.0,  1.0,
       -1.0,  1.0, -1.0,
     ];
   
     gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(positions), gl.STATIC_DRAW);
   
     // Color buffer setup
     const colorBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, colorBuffer);
   
     const faceColors = [
       [1.0, 1.0, 1.0, 1.0],    // Front face: white
       [1.0, 0.0, 0.0, 1.0],    // Back face: red
       [0.0, 1.0, 0.0, 1.0],    // Top face: green
       [0.0, 0.0, 1.0, 1.0],    // Bottom face: blue
       [1.0, 1.0, 0.0, 1.0],    // Right face: yellow
       [1.0, 0.0, 1.0, 1.0],    // Left face: purple
     ];
   
     let colors = [];
   
     for (let i = 0; i < faceColors.length; i++) {
       const color = faceColors[i];
   
       // Repeat each color four times for the four vertices of the face
       colors = colors.concat(color, color, color, color);
     }
   
     gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(colors), gl.STATIC_DRAW);
   
     // Texture coordinate buffer setup
     const textureCoordBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ARRAY_BUFFER, textureCoordBuffer);
   
     const textureCoordinates = [
       // Front face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
       // Back face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
       // Top face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
       // Bottom face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
       // Right face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
       // Left face
       0.0,  0.0,
       1.0,  0.0,
       1.0,  1.0,
       0.0,  1.0,
     ];
   
     gl.bufferData(gl.ARRAY_BUFFER, new Float32Array(textureCoordinates), gl.STATIC_DRAW);
   
     // Index buffer setup
     const indexBuffer = gl.createBuffer();
     gl.bindBuffer(gl.ELEMENT_ARRAY_BUFFER, indexBuffer);
   
     const indices = [
       0,  1,  2,    0,  2,  3,    // front
       4,  5,  6,    4,  6,  7,    // back
       8,  9,  10,   8,  10, 11,   // top
       12, 13, 14,   12, 14, 15,   // bottom
       16, 17, 18,   16, 18, 19,   // right
       20, 21, 22,   20, 22, 23,   // left
     ];
   
     gl.bufferData(gl.ELEMENT_ARRAY_BUFFER, new Uint16Array(indices), gl.STATIC_DRAW);
   
     return {
       position: positionBuffer,
       color: colorBuffer,
       textureCoord: textureCoordBuffer,
       indices: indexBuffer,
     };
   }   