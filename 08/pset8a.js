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

let min_zeroes = 25, min_ones = 0, min_twos = 0;
let count_zeroes = 0, count_ones = 0, count_twos = 0;
for (let i = 0; i < images.length; ++i) { // For each layer
    for(let j = 0; j < images[i].length; ++j) { // For each row
        for (let k = 0; k < images[i][j].length; ++k) { // For each column
            switch (images[i][j][k]) {
                case '0': ++count_zeroes; break;
                case '1': ++count_ones; break;
                case '2': ++count_twos; break;
            }
        }
    }
    if (count_zeroes < min_zeroes) {
        min_zeroes = count_zeroes;
        min_ones = count_ones;
        min_twos = count_twos;
    }
    count_zeroes = count_ones = count_twos = 0;
}

console.log(min_ones * min_twos);