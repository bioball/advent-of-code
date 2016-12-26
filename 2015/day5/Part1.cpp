#include <iostream>
#include <regex>
#include <fstream>

using namespace std;

bool isVowel(char c)
{
  return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
}

bool containsThreeVowels(string str)
{
  int vowelCount(0);
  for (int i(0); i < str.length(); i = i + 1)
  {
    if (isVowel(str[i]))
    {
      vowelCount++;
    }
    if (vowelCount >= 3)
    {
      return true;
    }
  }
  return false;
}

bool hasDoubleLetter(string str)
{
  char prev;
  char current;
  for (int i (1); i < str.length(); i = i + 1)
  {
    current = str[i];
    prev = str[i - 1];
    if (prev == current)
    {
      return true;
    }
  }
  return false;
}

bool doesNotHaveForbidden(string str)
{
  string forbidden[] = {"ab", "cd", "pq", "xy"};
  string current;
  for (int i(0); i < str.length() - 1; i = i + 1)
  {
    current = string() + str[i] + str[i + 1];
    bool exists = find(begin(forbidden), end(forbidden), current) != end(forbidden);
    if (exists)
    {
      return false;
    }
  }
  return true;
}

bool isNice(string str)
{
  return containsThreeVowels(str) && hasDoubleLetter(str) && doesNotHaveForbidden(str);
}

int main(int argc, char** argv)
{
  string src = argv[1];
  fstream file(src);
  int result (0);
  string line;
  while (getline(file, line))
  {
    if (isNice(line))
    {
      result += 1;
    }
  }
  cout << to_string(result) << endl;
  return 0;
}