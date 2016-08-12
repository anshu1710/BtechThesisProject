package pr;
public class AppApplet extends java.applet.Applet
{
    static final long serialVersionUID = 0L;
    
    public static abstract class View
    {
        private Canvas m_canvas;
        
        public void paint(java.awt.Graphics g, int w, int h)
        {
        }
        
        public void processKeyEvent(java.awt.Component comp, java.awt.event.KeyEvent e)
        {
        }
        
        public void processMouseEvent(java.awt.Component comp, java.awt.event.MouseEvent e)
        {
        }
        
        void activate(Canvas c)
        {
            m_canvas = c;
        }
        
        void setView(View v)
        {
            m_canvas.setView(v);
        }
    }
    
    private View m_view;
    private Canvas m_canvas;
    
    public AppApplet(View v)
    {
        m_view = v;
    }
    
    public void init()
    {
        m_canvas = new Canvas(m_view, getWidth(), getHeight());
        add(m_canvas);
        m_canvas.requestFocusInWindow();
        setVisible(true);
    }

    public void stop()
    {
    }

    public void resize(int width, int height)
    { 
        if (m_canvas != null)
            m_canvas.setPreferredSize(width, height);
        super.resize(width, height);
    }

    static int round(double d)
    {
        return (int) (d + 0.5);
    }
    
    public static class Canvas extends java.awt.Canvas
    {
        static final long serialVersionUID = 0L;
        
        private View m_view = null;
        private int m_width;
        private int m_height;
        
        Canvas(View v, int w, int h)
        {
            m_width = w;
            m_height = h;
            setView(v);
            enableEvents(java.awt.AWTEvent.KEY_EVENT_MASK | java.awt.AWTEvent.MOUSE_EVENT_MASK | java.awt.AWTEvent.MOUSE_MOTION_EVENT_MASK | java.awt.AWTEvent.MOUSE_WHEEL_EVENT_MASK);
        }
    
        void setView(View v)
        {
            if (m_view != null)
                m_view.activate(null);
            m_view = v;
            if (m_view != null)
                m_view.activate(this);
            repaint();
        }

        public void setPreferredSize(int w, int h)
        {
            m_width = w;
            m_height = h;
        }
        
        public java.awt.Dimension getPreferredSize()
        {
            System.out.println("Canvas::getPreferredSize " + m_width + " " + m_height);
            if (m_width > 0 && m_height > 0)
                return new java.awt.Dimension(m_width, m_height);
            else
                return super.getPreferredSize();
        }
        
        public void addNotify()
        {
            super.addNotify();
            createBufferStrategy(2);
        }
        
        public void repaint()
        {
            java.awt.image.BufferStrategy strategy = getBufferStrategy();
            if (strategy != null)
            {
                java.awt.Graphics ng = strategy.getDrawGraphics();
                paint(ng);
                ng.dispose();
                strategy.show();
            }
            else
                super.repaint();
        }
        
        public void paint(java.awt.Graphics g)
        {
            m_view.paint(g, getWidth(), getHeight());
        }

        protected void processKeyEvent(java.awt.event.KeyEvent e)
        {
            m_view.processKeyEvent(this, e);
        }

        protected void processMouseEvent(java.awt.event.MouseEvent e)
        {
            m_view.processMouseEvent(this, e);
        }
        
        protected void processMouseMotionEvent(java.awt.event.MouseEvent e)
        {
            m_view.processMouseEvent(this, e);
        }
        
        protected void processMouseWheelEvent(java.awt.event.MouseEvent e)
        {
            m_view.processMouseEvent(this, e);
        }
    }
    
    public static java.awt.geom.Rectangle2D getStringBounds(java.awt.Graphics g, String s)
    {
        java.awt.FontMetrics fm = g.getFontMetrics();
        return fm.getStringBounds(s, g);
    }
    
    public static void drawStringCentered(java.awt.Graphics g, String s, int x, int y, int w, int h)
    {
        java.awt.FontMetrics fm = g.getFontMetrics();
        java.awt.geom.Rectangle2D r = getStringBounds(g, s);
        //System.out.println("Rectangle2D " + r.getWidth() + " " + r.getHeight());
        //g.drawRect(round((w - r.getWidth()) / 2), round((h - r.getHeight()) / 2), round(r.getWidth()), round(r.getHeight()));
        g.drawString(s, x + round((w - r.getWidth()) / 2), y + round((h - r.getHeight()) / 2) + fm.getAscent());
    }
    
    static java.awt.Frame createFrame(String title, View v, int w, int h) throws Exception
    {
        java.awt.Frame f = null;
        try
        {
            f = new java.awt.Frame(title);
            f.addWindowListener(new java.awt.event.WindowAdapter()
                {
                    public void windowClosing(java.awt.event.WindowEvent e)
                    {
                        //System.out.println("Closing");
                        java.awt.Window w = e.getWindow();
                        w.dispose();
                    }
                });
            
            java.awt.Component comp = new Canvas(v, w, h);

            f.add(comp);
            f.pack();
            comp.requestFocusInWindow();
            f.setVisible(true);
        }
        catch (Exception e)
        {
            if (f != null)
                f.dispose();
            f = null;
            throw e;
        }
        return f;
    }
}
