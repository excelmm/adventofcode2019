#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cmath>

using namespace std;

int64_t intCode(std::vector<int64_t>, int64_t, int64_t, int64_t);

int main() {
    std::ifstream myfile("input.txt");
    std::stringstream rawinput;
    rawinput << myfile.rdbuf();

    std::vector<int64_t> computer;
    std::string token;
    
    while(getline(rawinput, token, ',')) {
        computer.push_back(std::stoll(token));
    }
    for (int i = computer.size(); i < 100000; ++i) {
        computer.push_back(0);
    }

    int64_t result = intCode(computer, 1, 1, 0);
    return 0;

}

int64_t intCode(std::vector<int64_t> computer, int64_t first_input, int64_t second_input, int64_t relative_base) {
    int64_t index = 0, result = 0, input = 0;
    while (true) {
        int64_t current = computer[index];
        int64_t op = current % 100;
        if (op == 99) return 0;
        int64_t mode1 = ((current / 100) % 10);
        int64_t mode2 = ((current / 1000) % 10);
        int64_t mode3 = ((current / 10000) % 10);
        int64_t par1 = 0, par2 = 0, par3 = 0;
        if (mode1 == 1) {
            par1 = computer[index + 1];
        } else if (mode1 == 2) {
            par1 = computer[computer[index + 1] + relative_base];
        } else {
            par1 = computer[computer[index + 1]];
        }
        if (mode2 == 1) {
            par2 = computer[index + 2];
        }
        else if (computer[index + 2] < 100) {
            if (mode2 == 2) {
                    par2 = computer[computer[index + 2] + relative_base];
                } else {
                    par2 = computer[computer[index + 2]];
                }
        }
        if (mode3 == 2) {
            par3 = computer[index + 3] + relative_base;
        } else {
            par3 = computer[index + 3];
        }
        switch (op) {
            case 1: 
                computer[par3] = par1 + par2; break;
            case 2: 
                computer[par3] = par1 * par2; break;
            case 3: 
                if (mode1 == 2) {
                    computer[computer[index + 1] + relative_base] = first_input;
                } else {
                    computer[computer[index + 1]] = first_input;                    
                }
                break;
            case 4: 
                result = par1;
                cout << par1 << endl;
                break;
            case 5: 
                if (par1 != 0) index = par2; else index += 3; break;
            case 6: 
                if (par1 == 0) index = par2; else index += 3; break;
            case 7: 
                if (par1 < par2) computer[par3] = 1; else computer[par3] = 0; break;
            case 8: 
                if (par1 == par2) computer[par3] = 1; else computer[par3] = 0; break;
            case 9:
                relative_base += par1; break;
        }
        if (op == 5 || op == 6) continue;
        if (op == 3 || op == 4 || op == 9) index += 2; else index += 4;
    }
}