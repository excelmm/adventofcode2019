#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <cmath>

using namespace std;

long long intCode (std::vector<int>&, int, int);

int main() {
    std::ifstream myfile("C:\\Users\\Excel PC\\Documents\\GitHub\\excelmm\\adventofcode2019\\07\\input.txt");
    std::stringstream rawinput;
    rawinput << myfile.rdbuf();

    std::vector<int> computer;
    std::string token;
    
    while(getline(rawinput, token, ',')) {
        computer.push_back(stoi(token));
    }
    
    std::vector<std::vector<int>> computers;

    for (int i = 0; i < 5; ++i) {
        std::vector<int> new_computer = computer;
        computers.push_back(new_computer);
    }

    std::vector<int> configs;
    for (int i = 56789; i < 100000; ++i) {
        int count5 = 0, count6 = 0, count7 = 0, count8 = 0, count9 = 0;
        int number = i;
        for (int j = 0; j < 5; ++j) {
            switch (number % 10) {
                case 5: 
                    count5++;
                    break;
                case 6: 
                    count6++;
                    break;
                case 7: 
                    count7++;
                    break;
                case 8: 
                    count8++;
                    break;
                case 9: 
                    count9++;
                    break;
            }
            number /= 10;
        }
        if (count5 && count6 && count7 && count8 && count9) configs.push_back(i);
    }

    int max = 0;
    std::string maxconfig;

    std::vector<int> computer_copy = computer;

    for (int i = 0; i < configs.size(); ++i) {

        // Restart computers
        for (int j = 0; j < 5; ++j) {
            computers[i] = computer_copy;
        }

        // Start testing new sequence
        int programinput = 0;
        int tempconfig = configs[i];
        int config[5] = {tempconfig % 10, (tempconfig / 10) % 10, (tempconfig / 100) % 10, (tempconfig / 1000) % 10, tempconfig / 10000};

        bool first_loop = true;
        bool halted = false;
        while (!halted) {
            for (int j = 0; j < 5; ++j) {

                int user_input = 0;
                // if (first_loop) user_input = config[j]; else user_input = programinput;
                user_input = config[j];
                long long next_program_input = intCode(computers[j], user_input, programinput);

                programinput = next_program_input;
                if (next_program_input == -1) {
                    halted = true;
                }
                if (halted) {
                    cout << "halted" << endl;
                    break;
                }
            }
            first_loop = false;
        }
        if (programinput > max) {
            max = programinput;
            maxconfig.clear();
            for (int i = 0; i < 5; ++i) {
                maxconfig.append(to_string(config[i]));
            }
        }
    }

    cout << max << " using sequence: " << maxconfig << endl;

    return 0;

}

long long intCode(std::vector<int> &computer, int first_input, int second_input) {
    int i = 0, input = 0, output = 0;
    long long result = 0;
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