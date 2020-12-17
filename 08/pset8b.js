const fs = require('fs');
const raw_input = fs.readFileSync('input.txt', 'utf-8');

const images = [];
let rows = [];
let row = [];
for (let i = 0; i < raw_input.length; ++i) {
    row.push(raw_input.charAt(i));
    if ((i + 1) % 25 === 0) {
        rows.push(row);
        row = [];
    }
    if ((i + 1) % 150 === 0) {
        images.push(rows);
        rows = [];
    }
}

let image = [];
for (let i = 0; i < 6; ++i) {
    row = [];
    for (let j = 0; j < 25; ++j)
        row.push('2');
    image.push(row);
}

for (let a = 0; a < images.length; ++a)
    for (let i = 0; i < images[a].length; ++i)
        for (let j = 0; j < images[a][i].length; ++j)
            if (image[i][j] === '2') image[i][j] = images[a][i][j];

for (let i = 0; i < image.length; ++i) {
    for (let j = 0; j < image[i].length; ++j) {
        if (image[i][j] === '0') process.stdout.write(' '); else process.stdout.write(image[i][j]);
    }
    console.log('');
}