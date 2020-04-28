package gds.project.framework;

import gds.project.framework.Graphics.PixmapFormat;

public interface Pixmap {
    public int getWidth();
 
    public int getHeight();
    
    public PixmapFormat getFormat();
    
    public void dispose();
}
