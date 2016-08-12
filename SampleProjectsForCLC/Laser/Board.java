package pr;
class Board
{
    enum piece {
        B_EMPTY,
        B_LASER_H,
        B_LASER_V,
        B_LASER_P,
        B_MIRROR1,
        B_MIRROR2,
        B_HIDDEN,
    };
    
    class Cell
    {
        piece mirror;
        piece hidden;
    };

    private Cell[][] m_data;
    private java.util.Random m_rand;
    
    Board(java.util.Random r, int w, int h, int mirror_count)
    {
        m_rand = r;
        m_data = new Cell[w][h];
        
        for (int y = 0; y < h; ++y)
        {
            for (int x = 0; x < w; ++x)
            {
                m_data[x][y] = new Cell();
                m_data[x][y].mirror = piece.B_EMPTY;
                m_data[x][y].hidden = piece.B_HIDDEN;
            }
        }
        
        for (int i = 0; i < mirror_count; ++i)
        {
            int x = m_rand.nextInt(w);
            int y = m_rand.nextInt(h);
            Cell c = m_data[x][y];
            if (c.mirror == piece.B_EMPTY)
                c.mirror = piece.values()[m_rand.nextInt(2) + piece.B_MIRROR1.ordinal()];
            else
                --i;
        }
    }
    
    int width()
    {
        return m_data.length;
    }
    
    int height()
    {
        return m_data[0].length;
    }
    
    Cell get(int x, int y)
    {
        return m_data[x][y];
    }
    
    boolean valid(int x, int y)
    {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }
}
