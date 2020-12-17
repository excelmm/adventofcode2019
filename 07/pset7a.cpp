#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cmath>

using namespace std;

int intCode (std::vector<int>, int, int);

int main() {
    std::ifstream myfile("input.txt");
    std::stringstream rawinput;
    rawinput << myfile.rdbuf();

    std::vector<int> computer;
    std::string token;
    
    while(getline(rawinput, token, ',')) {
        computer.push_back(stoi(token));
    }

    std::vector<int> configs;
    for (int i = 10000; i < 100000; ++i) {
        int count1 = 0, count2 = 0, count3 = 0, count4 = 0, count5 = 0;
        int number = i;
        for (int j = 0; j < 5; ++j) {
            switch (number % 10) {
                case 1: 
                    count1++;
                    break;
                case 2: 
                    count2++;
                    break;
                case 3: 
                    count3++;
                    break;
                case 4: 
                    count4++;
                    break;
                case 5: 
                    count5++;
                    break;
            }
            number /= 10;
        }
        if (count1 && count2 && count3 && count4 && count5) configs.push_back(i);
    }

    int max = 0;
    std::string maxconfig;

    std::vector<int> computer_copy = computer;

    for (int i = 0; i < configs.size(); ++i) {
        int programinput = 0;
        int tempconfig = configs[i] - 11111;
        int config[5] = {tempconfig % 10, (tempconfig / 10) % 10, (tempconfig / 100) % 10, (tempconfig / 1000) % 10, tempconfig / 10000};
        int initialconfig[5] = {tempconfig % 10, (tempconfig / 10) % 10, (tempconfig / 100) % 10, (tempconfig / 1000) % 10, tempconfig / 10000};

        for (int j = 0; j < 5; ++j) {
            std::vector<int> computer = computer_copy;
            int userinput = config[j];
            int nextprograminput = intCode(computer, userinput, programinput);
            // cout << nextprograminput << endl;
            if (nextprograminput == -1) {
                break;
            }
            programinput = nextprograminput;
            computer.clear();
        }
        if (programinput > max) {
            max = programinput;
            maxconfig.clear();
            for (int i = 0; i < 5; ++i) {
                maxconfig.append(to_string(initialconfig[i]));
            }
        }
    }

    cout << max << " using sequence: " << maxconfig << endl;

    return 0;

}

int intCode(std::vector<int> computer, int first_input, int second_input) {
    int i = 0, result = 0, input = 0, output = 0;
    while (true) {
        int current = computer[i];
        int op = current % 100;
        int mode1 = ((current / 100) % 10);
        int mode2 = ((current / 1000) % 10);
        int mode3 = ((current / 10000) % 10);
        int par1 = 0, par2 = 0, par3 = 0;
        if (mode1 == 1) par1 = computer[i + 1]; else par1 = computer[computer[i + 1]];
        if (computer[i + 2] < 100) {
            if (mode2 == 1) par2 = computer[i + 2]; else par2 = computer[computer[i + 2]];
        }
        if (computer[i + 3] < 100) {
            if (mode3 == 1) par3 = i + 3; else par3 = computer[i + 3];
        }
        switch (op) {
            case 1: 
                computer[par3] = par1 + par2;
                break;
            case 2: 
                computer[par3] = par1 * par2;
                break;
            case 3: 
                if (input) {
                    computer[computer[i + 1]] = second_input;
                }
                else {
                    computer[computer[i + 1]] = first_input;
                    ++input;
                }
                break;
            case 4: 
                ++output;
                result = par1;
                // cout << par1 << endl;
                break;
            case 5: 
                if (par1 != 0) i = par2; else i += 3;
                break;
            case 6: 
                if (par1 == 0) i = par2; else i += 3;
                break;
            case 7: 
                if (par1 < par2) computer[par3] = 1; else computer[par3] = 0;
                break;
            case 8: 
                if (par1 == par2) computer[par3] = 1; else computer[par3] = 0;
                break;
            default:
                return -1;
        }
        if (op == 5 || op == 6) continue;
        if (op == 3 || op == 4) i += 2; else i += 4;
        if (output) {
            return result;
        }
    }
}