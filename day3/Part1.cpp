//
// Created by Daniel Chao on 12/18/15.
//

#include <iostream>
#include <set>
#include <sstream>
#include <string>
#include <fstream>

using namespace std;


class Point
{
public:
    int x;
    int y;
    Point(int a, int b)
    {
        x = a;
        y = b;
    };

    void moveUp () {
        y += 1;
    };
    void moveDown () {
        y -= 1;
    };
    void moveLeft () {
        x -= 1;
    };
    void moveRight () {
        x += 1;
    };

    string toString () const
    {
        return to_string(x) + ", " + to_string(y);
    }

    bool operator<(const Point& other) const
    {
        if (y != other.y) {
            return y < other.y;
        }
        return x < other.x;
    }
};

struct compare {
    bool operator() (const Point& lhs, const Point& rhs) const
    {
        return lhs < rhs;
    }
};

set<Point, compare> grid;
Point currentPoint(0,0);

ifstream *openfile(string src)
{
    ifstream *file = new ifstream();
    file -> open(src);
    return file;
}

void iterateThroughDirections(ifstream *file) {
    grid.insert(currentPoint);
    while (!file -> eof())
    {
        char direction;
        file -> get(direction);
        switch (direction)
        {
            case '^':
                currentPoint.moveUp();
                break;
            case '>':
                currentPoint.moveRight();
                break;
            case 'v':
                currentPoint.moveDown();
                break;
            case '<':
                currentPoint.moveLeft();
                break;
        }
        grid.insert(currentPoint);
    }
}

int main(int argc, char** argv)
{
    string src = argv[1];
    string line;
    ifstream *file = openfile(src);
    iterateThroughDirections(file);
    cout << grid.size() << endl;
}