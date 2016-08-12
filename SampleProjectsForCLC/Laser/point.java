package pr;

class point
{
    enum dir {
        DIR_NORTH,
        DIR_EAST,
        DIR_SOUTH,
        DIR_WEST,
        DIR_NONE,
    };

    point()
    {
    }

    point(point p)
    {
        x = p.x;
        y = p.y;
    }

    point(int sx, int sy)
    {
        x = sx;
        y = sy;
    }

    void move(dir d)
    {
        switch (d)
        {
        case DIR_NORTH:
            --y;
            break;
        case DIR_EAST:
            ++x;
            break;
        case DIR_SOUTH:
            ++y;
            break;
        case DIR_WEST:
            --x;
            break;
        };
    }

    int x;
    int y;
};
