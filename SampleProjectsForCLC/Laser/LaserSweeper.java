package pr;
public class LaserSweeper extends AppApplet
{
    static final long serialVersionUID = 0L;
    
    public LaserSweeper()
    {
        super(new MenuView());
        enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK);
    }
    
    static public void main(String[] args) throws Exception
    {
        java.awt.Frame f = createFrame("Laser Sweeper", new MenuView(), 440, 470);
    }
        
    private static int fix_range(int c, int min, int max)
    {
        if (c < min)
            return min;
        else if (c > max)
            return max;
        else
            return c;
    }

    final static java.awt.Font m_end_font = new java.awt.Font("Serif", java.awt.Font.BOLD, 30);
    final static java.awt.Font m_status_font = new java.awt.Font("Serif", java.awt.Font.PLAIN, 15);
    final static java.awt.Font m_title_font = new java.awt.Font("Serif", java.awt.Font.BOLD, 30);
    final static java.awt.Font m_item_font = new java.awt.Font("Serif", java.awt.Font.PLAIN, 15);
    
    static public class LaserSweeperView extends View
    {
        final static java.util.Random m_rand = new java.util.Random();
        Board m_board;
        final int m_width;
        final int m_height;
        final View m_back;
        final int m_mirror_count;
        boolean m_error;
        point m_laser_begin;
        point m_laser_end;
        point m_cursor;
        int m_hcount;
        int m_mguess;
        int m_laser_length;

        final int m_status_height = 30;
        int m_tile_width;
        int m_tile_height;
        int m_x_offset;
        int m_y_offset;
        
        enum draw_mode
        {
            DRAW_ERASE,
            DRAW_LASER,
        };

        LaserSweeperView(int w, int h, View back)
        {
            m_width = w;
            m_height = h;
            m_back = back;
            m_mirror_count = (m_width * m_height) / 10;
            m_cursor = new point(0, 0);
            init_game();
        }
        
        void init_game()
        {
            m_error = false;
            m_mguess = 0;
            m_board = new Board(m_rand, m_width, m_height, m_mirror_count);
            m_hcount = m_board.width() * m_board.height() - m_mirror_count;
            
            switch (m_rand.nextInt(4))
            {
            case 0: // TOP
                m_laser_begin = new point(m_rand.nextInt(m_board.width()), -1);
                break;

            case 1: // BOTTOM
                m_laser_begin = new point(m_rand.nextInt(m_board.width()), m_board.height());
                break;

            case 2: // LEFT
                m_laser_begin = new point(-1, m_rand.nextInt(m_board.height()));
                break;

            case 3: // RIGHT
                m_laser_begin = new point(m_board.width(), m_rand.nextInt(m_board.height()));
                break;

            default:
                assert(false);
                break;
            }
            
            m_laser_end = draw_laser(draw_mode.DRAW_LASER);
        }
        
        public void paint(java.awt.Graphics g, int w, int h)
        {
            //m_tile_width  = w / (m_board.width() + 2);
            //m_tile_height = (h - m_status_height) / (m_board.height() + 2);
            m_tile_width  = java.lang.Math.min(w / (m_board.width() + 2), (h - m_status_height) / (m_board.height() + 2));
            m_tile_height = m_tile_width;
            
            m_x_offset = (w - (m_board.width() + 2) * m_tile_width) / 2;
            m_y_offset = (h - m_status_height - (m_board.height() + 2) * m_tile_height) / 2;
            
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, w, h);
            g.translate(m_x_offset, m_y_offset);
            draw_board(g, m_tile_width, m_tile_height);
            g.translate(-m_x_offset, -m_y_offset);
            g.setColor(java.awt.Color.WHITE);
            g.setFont(m_end_font);
            if (m_hcount == 0)
                drawStringCentered(g, "You won!", 0, 0, w, h - m_status_height);
            else if (m_error)
                drawStringCentered(g, "You lost... try again", 0, 0, w, h - m_status_height);
            g.setFont(m_status_font);
            drawStringCentered(g, "Spaces: " + m_hcount + "   Mirrors: " + m_mguess + "/" + m_mirror_count + " Length: " + m_laser_length, 0, h - m_status_height, w, m_status_height);
            
            g.setColor(java.awt.Color.WHITE);
            //drawStringCentered(g, "Offset " + (h - m_status_height) + " " + m_y_offset + " " + m_tile_height + " " + ((m_board.height() + 2) * m_tile_height), 0, 0, w, 40);
            drawStringCentered(g, "Size " + w + " " + h, 0, 0, w, 40);
        }
        
        point.dir get_next_dir(point.dir d, Board.piece pb)
        {
            switch(pb)
            {
            case B_MIRROR1:
                switch (d)
                {
                case DIR_NORTH:
                    d = point.dir.DIR_EAST;
                    break;
                case DIR_EAST:
                    d = point.dir.DIR_NORTH;
                    break;
                case DIR_SOUTH:
                    d = point.dir.DIR_WEST;
                    break;
                case DIR_WEST:
                    d = point.dir.DIR_SOUTH;
                    break;
                };
                break;

            case B_MIRROR2:
                switch (d)
                {
                case DIR_NORTH:
                    d = point.dir.DIR_WEST;
                    break;
                case DIR_EAST:
                    d = point.dir.DIR_SOUTH;
                    break;
                case DIR_SOUTH:
                    d = point.dir.DIR_EAST;
                    break;
                case DIR_WEST:
                    d = point.dir.DIR_NORTH;
                    break;
                };
                break;
            }
            return d;
        }
        
        point draw_laser(draw_mode mode)
        {
            m_laser_length = 0;
            
            point p = new point(m_laser_begin);
            point.dir d = get_edge(p.x, p.y);
            if (d == point.dir.DIR_NONE)
                return p;

            p.move(d);
            do {
                Board.Cell c = m_board.get(p.x, p.y);
                Board.piece pb = c.mirror;
                d = get_next_dir(d, pb);
                switch(pb)
                {
                case B_LASER_H:
                case B_LASER_V:
                case B_LASER_P:
                    switch(mode)
                    {
                    case DRAW_ERASE:
                        c.mirror = Board.piece.B_EMPTY;
                        break;

                    case DRAW_LASER:
                        c.mirror = Board.piece.B_LASER_P;
                        break;
                    }
                    break;

                case B_EMPTY:
                    switch(mode)
                    {
                    case DRAW_LASER:
                        switch (d)
                        {
                        case DIR_NORTH:
                        case DIR_SOUTH:
                            c.mirror = Board.piece.B_LASER_V;
                            break;
                        case DIR_EAST:
                        case DIR_WEST:
                            c.mirror = Board.piece.B_LASER_H;
                            break;
                        };
                        break;
                    }
                    break;
                }
                ++m_laser_length;
                p.move(d);
            } while (m_board.valid(p.x, p.y));
            return p;
        }
        
        void clear_laser()
        {
            point p = new point(m_laser_begin);
            point.dir d = get_edge(p.x, p.y);
            if (d == point.dir.DIR_NONE)
                return;

            p.move(d);
            do {
                Board.Cell c = m_board.get(p.x, p.y);
                Board.piece pb = c.hidden;
                d = get_next_dir(d, pb);
                switch(pb)
                {
                case B_HIDDEN:
                    if (c.mirror == Board.piece.B_MIRROR1 || c.mirror == Board.piece.B_MIRROR2)
                        m_error = true;
                    else
                    {
                        c.hidden = Board.piece.B_EMPTY;
                        --m_hcount;
                    }
                    break;
                }
                p.move(d);
            } while (m_board.valid(p.x, p.y) && !m_error);
        }

        point.dir get_edge(int x, int y)
        {
            if (x == -1 && y >= 0 && y < m_board.height())
                return point.dir.DIR_EAST;
            if (x == m_board.width() && y >= 0 && y < m_board.height())
                return point.dir.DIR_WEST;
            if (y == -1 && x >= 0 && x < m_board.width())
                return point.dir.DIR_SOUTH;
            if (y == m_board.height() && x >= 0 && x < m_board.width())
                return point.dir.DIR_NORTH;
            return point.dir.DIR_NONE;
        }

        void draw_board(java.awt.Graphics g, int tile_width, int tile_height)
        {
            final java.awt.Color color_mirror_right = java.awt.Color.CYAN;
            final java.awt.Color color_mirror_wrong = java.awt.Color.BLUE;
            final java.awt.Color color_laser = java.awt.Color.RED;
            
            int hcount = 0;
            int mcount = 0;
            int mguess = 0;
            
            g.setColor(java.awt.Color.LIGHT_GRAY);
            for (int x = 0; x < m_board.width(); ++x)
            {
                g.fillRect((x + 1) * tile_width, 0 * tile_height,                     tile_width, tile_height);
                g.fillRect((x + 1) * tile_width, (m_board.height() + 1) * tile_height, tile_width, tile_height);
            }

            for (int y = 0; y < m_board.height(); ++y)
            {
                g.fillRect(0 * tile_width,                     (y + 1) * tile_height, tile_width, tile_height);
                g.fillRect((m_board.width() + 1) * tile_width, (y + 1) * tile_height, tile_width, tile_height);
            }

            for (int y = 0; y < m_board.height(); ++y)
            {
                for (int x = 0; x < m_board.width(); ++x)
                {
                    Board.Cell cb = m_board.get(x, y);
                    final int sx = (x + 1) * tile_width;
                    final int sy = (y + 1) * tile_height;

                    if (cb.hidden != Board.piece.B_EMPTY && cb.mirror != Board.piece.B_MIRROR1 && cb.mirror != Board.piece.B_MIRROR2)
                        ++hcount;
                    if (cb.mirror == Board.piece.B_MIRROR1 || cb.mirror == Board.piece.B_MIRROR2)
                        ++mcount;
                    if (cb.hidden == Board.piece.B_MIRROR1 || cb.hidden == Board.piece.B_MIRROR2)
                        ++mguess;

                    boolean draw_mirror = true;
                    if (!m_error)
                    {
                        switch(cb.hidden)
                        {
                        case B_MIRROR1:
                            g.setColor(java.awt.Color.GRAY);
                            g.fillRect(sx + 1, sy + 1, tile_width - 2, tile_height - 2);
                            g.setColor(color_mirror_right);
                            g.drawLine(sx, sy + tile_height - 1, sx + tile_width - 1, sy);
                            draw_mirror = false;
                            break;
                        case B_MIRROR2:
                            g.setColor(java.awt.Color.GRAY);
                            g.fillRect(sx + 1, sy + 1, tile_width - 2, tile_height - 2);
                            g.setColor(color_mirror_right);
                            g.drawLine(sx, sy, sx + tile_width - 1, sy + tile_height - 1);
                            draw_mirror = false;
                            break;
                        case B_HIDDEN:
                            g.setColor(java.awt.Color.GRAY);
                            g.fillRect(sx + 1, sy + 1, tile_width - 2, tile_height - 2);
                            draw_mirror = false;
                            break;
                        }
                    }
                    if (draw_mirror)
                    {
                        switch(cb.mirror)
                        {
                        case B_EMPTY:
                            if (cb.hidden == Board.piece.B_HIDDEN)
                            {
                                g.setColor(java.awt.Color.GRAY);
                                g.fillRect(sx + 1, sy + 1, tile_width - 2, tile_height - 2);
                            }
                            break;
                        case B_LASER_H:
                            g.setColor(color_laser);
                            g.drawLine(sx, sy + tile_height / 2, sx + tile_width - 1, sy + tile_height / 2);
                            break;
                        case B_LASER_V:
                            g.setColor(color_laser);
                            g.drawLine(sx + tile_width / 2, sy, sx + tile_width / 2, sy + tile_height - 1);
                            break;
                        case B_LASER_P:
                            g.setColor(color_laser);
                            g.drawLine(sx, sy + tile_height / 2, sx + tile_width - 1, sy + tile_height / 2);
                            g.drawLine(sx + tile_width / 2, sy, sx + tile_width / 2, sy + tile_height - 1);
                            break;
                        case B_MIRROR1:
                            if (cb.mirror == cb.hidden)
                                g.setColor(color_mirror_right);
                            else
                                g.setColor(color_mirror_wrong);
                            g.drawLine(sx, sy + tile_height - 1, sx + tile_width - 1, sy);
                            break;
                        case B_MIRROR2:
                            if (cb.mirror == cb.hidden)
                                g.setColor(color_mirror_right);
                            else
                                g.setColor(color_mirror_wrong);
                            g.drawLine(sx, sy, sx + tile_width - 1, sy + tile_height - 1);
                            break;
                        }
                    }
                }
            }
            
            g.setColor(color_laser);
            g.fillRect((m_laser_begin.x + 1) * tile_width, (m_laser_begin.y + 1) * tile_height, tile_width, tile_height);
            g.fillRect((m_laser_end.x + 1) * tile_width, (m_laser_end.y + 1) * tile_height, tile_width, tile_height);
            
            g.setColor(java.awt.Color.WHITE);
            g.drawRect((m_cursor.x + 1) * tile_width, (m_cursor.y + 1) * tile_height, tile_width - 1, tile_height - 1);
            
            if (m_mguess != mguess)
                System.out.println("mguess error " + mguess + " " + m_mguess);
            if (m_hcount != hcount)
                System.out.println("hcount error " + hcount + " " + m_hcount);
        }
        
        void reveal()
        {
            if (!m_error && m_board.valid(m_cursor.x, m_cursor.y))
            {
                Board.Cell c = m_board.get(m_cursor.x, m_cursor.y);
                if (c.hidden == Board.piece.B_HIDDEN)
                {
                    if (c.mirror == Board.piece.B_MIRROR1 || c.mirror == Board.piece.B_MIRROR2)
                        m_error = true;
                    else
                    {
                        c.hidden = Board.piece.B_EMPTY;
                        --m_hcount;
                    }
                }
            }
            else if (get_edge(m_cursor.x, m_cursor.y) != point.dir.DIR_NONE)
            {
                m_laser_end = draw_laser(draw_mode.DRAW_ERASE);
                m_laser_begin = new point(m_cursor);
                m_laser_end = draw_laser(draw_mode.DRAW_LASER);
            }
        }
        
        public void processKeyEvent(java.awt.Component comp, java.awt.event.KeyEvent e)
        {
            if (e.getID() == java.awt.event.KeyEvent.KEY_PRESSED)
            {
                switch (e.getKeyCode())
                {
                case java.awt.event.KeyEvent.VK_A:
                case java.awt.event.KeyEvent.VK_LEFT:
                    --m_cursor.x;
                    break;
                    
                case java.awt.event.KeyEvent.VK_D:
                case java.awt.event.KeyEvent.VK_RIGHT:
                    ++m_cursor.x;
                    break;
                    
                case java.awt.event.KeyEvent.VK_W:
                case java.awt.event.KeyEvent.VK_UP:
                    --m_cursor.y;
                    break;
                    
                case java.awt.event.KeyEvent.VK_S:
                case java.awt.event.KeyEvent.VK_DOWN:
                    ++m_cursor.y;
                    break;
                }                
                    
                m_cursor.x = fix_range(m_cursor.x, -1, m_board.width());
                m_cursor.y = fix_range(m_cursor.y, -1, m_board.height());
                comp.repaint();
            }
            
            if (e.getID() == java.awt.event.KeyEvent.KEY_RELEASED)
            {
                switch (e.getKeyCode())
                {
                //case java.awt.event.KeyEvent.VK_E:  // DEBUG only
                    //m_error = !m_error;
                    //break;
                    
                case java.awt.event.KeyEvent.VK_ESCAPE:
                case java.awt.event.KeyEvent.VK_Q:
                    setView(m_back);
                    break;
                    
                case java.awt.event.KeyEvent.VK_H:
                    setView(new HelpView(this));
                    break;
                    
                case java.awt.event.KeyEvent.VK_R:
                    init_game();
                    break;

                case java.awt.event.KeyEvent.VK_Z:
                    if (!m_error && m_board.valid(m_cursor.x, m_cursor.y))
                    {
                        Board.Cell c = m_board.get(m_cursor.x, m_cursor.y);
                        switch (c.hidden)
                        {
                        case B_HIDDEN:  c.hidden = Board.piece.B_MIRROR2; ++m_mguess; break;
                        case B_MIRROR1: c.hidden = Board.piece.B_HIDDEN;  --m_mguess; break;
                        case B_MIRROR2: c.hidden = Board.piece.B_MIRROR1;             break;
                        }
                    }
                    break;
                    
                case java.awt.event.KeyEvent.VK_X:
                    if (!m_error && m_board.valid(m_cursor.x, m_cursor.y))
                    {
                        Board.Cell c = m_board.get(m_cursor.x, m_cursor.y);
                        switch (c.hidden)
                        {
                        case B_HIDDEN:  c.hidden = Board.piece.B_MIRROR1; ++m_mguess; break;
                        case B_MIRROR1: c.hidden = Board.piece.B_MIRROR2;             break;
                        case B_MIRROR2: c.hidden = Board.piece.B_HIDDEN;  --m_mguess; break;
                        }
                    }
                    break;
                    
                case java.awt.event.KeyEvent.VK_C:
                    if (!m_error)
                    {
                        clear_laser();
                    }
                    break;

                case java.awt.event.KeyEvent.VK_SPACE:
                case java.awt.event.KeyEvent.VK_ENTER:
                    reveal();
                    break;
                }
                comp.repaint();
            }
        }
        
        boolean m_reveal = false;
        
        public void processMouseEvent(java.awt.Component comp, java.awt.event.MouseEvent e)
        {
                //System.out.println("Mouse " + e);
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_PRESSED && e.getButton() == java.awt.event.MouseEvent.BUTTON1)
                m_reveal = true;
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_RELEASED && e.getButton() == java.awt.event.MouseEvent.BUTTON1)
                m_reveal = false;
                
            {
                int bx = (e.getX() - m_x_offset) / m_tile_width - 1;
                int by = (e.getY() - m_y_offset) / m_tile_height - 1;
                if (bx >= -1 && bx <= m_board.width() && by >= -1 && by <= m_board.height())
                {
                    //System.out.println("Mouse " + bx + " " + by);
                    m_cursor.x = bx;
                    m_cursor.y = by;
                    if (m_reveal)
                        reveal();
                    comp.repaint();
                }
            }
        }
    }
    
    static public class MenuView extends View
    {
        int m_select = 0;
        final String m_menu[] = { "Easy", "Medium", "Hard", "Help" };
        int m_menu_begin;
        int m_menu_space = 0;
        
        public void paint(java.awt.Graphics g, int w, int h)
        {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, w, h);
            
            int y = 0;
            
            g.setColor(java.awt.Color.RED);
            g.setFont(m_title_font);
            String s = "Laser Sweeper";
            java.awt.geom.Rectangle2D r = getStringBounds(g, s);
            int hl = round(2 * r.getHeight());
            drawStringCentered(g, s, 0, y, w, hl);
            y += 2 * hl;
            
            g.setFont(m_item_font);
            r = getStringBounds(g, m_menu[0]);
            m_menu_begin = y;
            m_menu_space = round(2 * r.getHeight());
            for (int i = 0; i < m_menu.length; ++i)
            {
                if (i == m_select)
                {
                    g.setColor(java.awt.Color.WHITE);
                    g.fillRect(w/4, y, w/2, m_menu_space);
                    g.setColor(java.awt.Color.BLACK);
                }
                else
                    g.setColor(java.awt.Color.WHITE);
                drawStringCentered(g, m_menu[i], 0, y, w, m_menu_space);
                y += m_menu_space;
            }
        }
        
        void doMenu(int item)
        {
            switch (item)
            {
            case 0:
                setView(new LaserSweeperView(10, 10, this));
                break;
                
            case 1:
                setView(new LaserSweeperView(20, 20, this));
                break;
                
            case 2:
                setView(new LaserSweeperView(40, 40, this));
                break;
                
            case 3:
                setView(new HelpView(this));
                break;
            }
        }
        
        public void processKeyEvent(java.awt.Component comp, java.awt.event.KeyEvent e)
        {
            if (e.getID() == java.awt.event.KeyEvent.KEY_PRESSED)
            {
                switch (e.getKeyCode())
                {
                case java.awt.event.KeyEvent.VK_UP:
                    --m_select;
                    break;
                    
                case java.awt.event.KeyEvent.VK_DOWN:
                    ++m_select;
                    break;
                }                
                    
                m_select = fix_range(m_select, 0, m_menu.length - 1);
                comp.repaint();
            }
            
            if (e.getID() == java.awt.event.KeyEvent.KEY_RELEASED)
            {
                switch (e.getKeyCode())
                {
                case java.awt.event.KeyEvent.VK_H:
                    setView(new HelpView(this));
                    break;
                    
                case java.awt.event.KeyEvent.VK_SPACE:
                case java.awt.event.KeyEvent.VK_ENTER:
                    doMenu(m_select);
                    break;
                }
            }
        }
        
        public void processMouseEvent(java.awt.Component comp, java.awt.event.MouseEvent e)
        {
            if (m_menu_space <= 0)
                return ;
                
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED && e.getButton() == java.awt.event.MouseEvent.BUTTON1)
            {
                int select = (e.getY() - m_menu_begin) / m_menu_space;
                if (select >= 0 && select < m_menu.length)
                {
                    m_select = select;
                    comp.repaint();
                    doMenu(m_select);
                }
            }

            if (e.getID() == java.awt.event.MouseEvent.MOUSE_MOVED)
            {
                int select = (e.getY() - m_menu_begin) / m_menu_space;
                if (select >= 0 && select < m_menu.length)
                {
                    m_select = select;
                    comp.repaint();
                }
            }
        }
    }
    
    static public class HelpView extends View
    {
        final View m_back;
        
        HelpView(View back)
        {
            m_back = back;
        }
        
        public void paint(java.awt.Graphics g, int w, int h)
        {
            g.setColor(java.awt.Color.BLACK);
            g.fillRect(0, 0, w, h);
            
            int y = 0;
            
            g.setColor(java.awt.Color.RED);
            g.setFont(m_title_font);
            String s = "Laser Sweeper";
            java.awt.geom.Rectangle2D r = getStringBounds(g, s);
            int hl = round(2 * r.getHeight());
            drawStringCentered(g, s, 0, y, w, hl);
            y += (3 * hl) / 2;
            
            g.setFont(m_item_font);
            r = getStringBounds(g, "Test string");
            hl = round(2 * r.getHeight());

            g.setColor(java.awt.Color.WHITE);
            drawStringCentered(g, "arrow/wasd - move the cursor", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "spacebar/enter - reveal space", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "z/x - change mirror guess", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "c - clear along laser path", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "r - restart", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "h - help", 0, y, w, hl);
            y += hl;
            drawStringCentered(g, "escape/q - quit", 0, y, w, hl);
        }
        
        public void processKeyEvent(java.awt.Component comp, java.awt.event.KeyEvent e)
        {
            if (e.getID() == java.awt.event.KeyEvent.KEY_RELEASED)
            {
                switch (e.getKeyCode())
                {
                case java.awt.event.KeyEvent.VK_ESCAPE:
                case java.awt.event.KeyEvent.VK_Q:
                case java.awt.event.KeyEvent.VK_SPACE:
                case java.awt.event.KeyEvent.VK_ENTER:
                    setView(m_back);
                    break;
                }
            }
        }
        
        public void processMouseEvent(java.awt.Component comp, java.awt.event.MouseEvent e)
        {
            if (e.getID() == java.awt.event.MouseEvent.MOUSE_CLICKED)
                setView(m_back);
        }
    }
}
