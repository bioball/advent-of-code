#include "md5.h"
#include <iostream>
#include <sstream>

string input("bgvyzdsv");

bool beginsWithSixZeroes (string str)
{
    return str.substr(0,6) == "000000";
}

int main () {
    for (int i = 0; i < 100000000; i = i + 1)
    {
        string hashed = md5(input + to_string(i));
        if (beginsWithSixZeroes(hashed))
        {
            cout << to_string(i) << endl;
            return 0;
        }
    }
    cout << "unable to find a string that begins with six zeroes" << endl;
    return 1;
}