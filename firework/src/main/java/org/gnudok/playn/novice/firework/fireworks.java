package org.gnudok.playn.novice.firework;
/* ---------------------------------------------------------------------------------
 * Fireworks applet, Copyright (c) 1995 Erik Wistrand, All Rights Reserved.
 *
 * Permission to use, copy, modify, and distribute this software and its
 * documentation for NON-COMMERCIAL purposes and without fee is hereby
 * granted provided that this copyright notice and appropiate documention
 * appears in all copies.
 *
 * ERIK WISTRAND MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE
 * SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE, OR NON-INFRINGEMENT. ERIK WISTRAND SHALL NOT BE LIABLE
 * FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR
 * DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
 *
 * ---------------------------------------------------------------------------------
 * fireworks applet v2.0f (beta API)
 *
 * Supported actions
 *  - mouseDown()
 *      applet is restarted
 *
 * Supported tags</h3>
 *  - ROCKETS n
 *      Number of rockets to animate (default 3)
 *  - POINTS n
 *      Number of points in each rocket (default 3)
 *  - POINTSIZE n
 *      Pixelsize of points (default 2)
 *  - LIFELENGTH n
 *      Duration of rockets, counted in number of frames (default 100)
 *  - GRAV n
 *      Gravity. Large value => rockets fall down fast (default 10)
 *  - DELAY n
 *      Delay in milliseconds between frames (default 50)
 *      (Warning: delay=0 consumes a lot of browser cpu time)
 *  - TRAIL n
 *      Number of trailing points (default 10)
 *  - COLOR rrggbb
 *      RGB hex triple for applet background (default browser background)
 *  - WIDTH n
 *      java.applet.Applet size().width in pixels (default tiny = browser default?)
 *  - HEIGHT n
 *      java.applet.Applet size().height in pixels (default tiny = browser default?)
 *
 *
 * Comments or problems with the fireworks applet?
 *   Contact me at:
 *
 *   wistrand@cs.chalmers.se
 *   URL: http://www.cs.chalmers.se/~wistrand
 *
 */

import java.awt.Graphics;
import java.awt.Color;

class Bullet {
  double xx;
  double yy;
  double x[] = null;
  double y[] = null;
  double dx;
  double dy;
  double g;
  double f         = 1.0f;
  int    lastx     = 0;
  int    lasty     = 0;
  int    dotsize;
  int    _trail    = 10;
  int    _life_len = 100;
  int    t         = 0;
  int    t2        = 0;
  int    count     = 0;
  Color  col       = Color.black;
  Color  remcol    = Color.white;

  double xmax;
  double ymax;

  Bullet(int x0,
	 int y0,
	 double dx0,
	 double dy0,
	 double g0,
	 int size,
	 int w,
	 int h,
	 int trail,
	 int life_len,
	 Color fg,
	 Color bg)
    {
      col    = fg;
      remcol = bg;

      _trail  = trail;
      _life_len    = life_len;

      t  = 0;
      t2 = 1;

      x = new double [_trail];
      y = new double [_trail];

      int i;

      for(i = 0; i < _trail; i++)
	{
	  x[i] = -dotsize * 2.0f;
	  y[i] = -dotsize * 2.0f;
	}

      xx      = x0;
      yy      = y0;
      dx      = dx0;
      dy      = dy0;
      g       = g0;
      dotsize = size;


      xmax = w;
      ymax = h;
    }

  void move()
    {
      xx += dx;
      yy += dy;

      dx *= f;
      dy *= f;
      dy += g;
    }

  boolean draw(Graphics g)
    {
      if(count++ < _life_len)
	{
	  x[t] = xx;
	  y[t] = yy;

	  int sx = (int)xx;
	  int sy = (int)yy;
	  g.setColor(col);

	  g.fillRect(sx, sy, dotsize, dotsize);

	  t++;
	  if(t >= _trail) t = 0;

	  return ((sx >= 0)   && (sy >= 0) &&
		  (sx < xmax) && (sy < ymax));
	}

      return (t2 != t);
    }

  void undraw(Graphics g)
    {
      int sx = (int)x[t2];
      int sy = (int)y[t2];

      t2++;
      if(t2 >= _trail) t2 = 0;

      g.setColor(remcol);
      g.fillRect(sx, sy, dotsize, dotsize);
    }

  void clearall(Graphics g)
    {
      int i = 0;

      while(i < _trail)
	{
	  undraw(g);
	  i++;
	}
    }
}

class Rocket {
  Bullet bullets[];

  int    _nof_bullets;
  int    _xmax;
  int    _ymax;
  int    _size;
  double _g;
  int    _trail;
  int    _life_len;
  Color  _fg;
  Color  _bg;

  Rocket(int    nof_points,
	 int    x0,
	 int    y0,
	 double dx0,
	 double dy0,
	 double g,
	 int    dotsize,
	 int    w,
	 int    h,
	 int    trail,
	 int    life_len,
	 Color  fg,
	 Color  bg)
    {
      _fg = fg;
      _bg = bg;
      _nof_bullets = nof_points;
      _g           = g;
      _xmax        = w;
      _ymax        = h;
      _size        = dotsize;
      _trail       = trail;
      _life_len    = life_len;
      bullets = new Bullet[_nof_bullets];

      reinit(x0, y0, dx0, dy0);
    }

  void reinit(int    x0,
	      int    y0,
	      double dx0,
	      double dy0)
    {
      int i;

      for(i = 0; i < _nof_bullets; i++)
	{
	  double a = Math.random() * 2.0f * 3.1415f;
	  double r = Math.random() * 1.0f;

	  double dx = Math.cos(a) * r;
	  double dy = Math.sin(a) * r;

	  bullets[i] = new Bullet(x0, y0,
				  dx0 + dx,
				  dy0 + dy,
				  _g,
				  _size,
				  _xmax, _ymax,
				  _trail,
				  _life_len,
				  _fg, _bg);
	}
    }

  void draw(Graphics g)
    {
      int i;
      boolean inside = false;
      for(i = 0; i < _nof_bullets; i++)
	{
	  inside = bullets[i].draw(g) || inside;
	  bullets[i].move();
	}

      if(!inside)
	{
	  int x0 = 10 + (int)(Math.random() * (double)(_xmax - 20));
	  int y0 = _ymax - (int)(Math.random() * 20.0f);

	  double dx0 = (Math.random() - 0.5f) * 2.0f;
	  double dy0 = -(Math.random() * 6);

	  reinit(x0, y0, dx0, dy0);
	}
    }

  void update(Graphics g)
    {
      int i;
      boolean inside = false;

      for(i = 0; i < _nof_bullets; i++)
	{
	  bullets[i].undraw(g);
	  bullets[i].move();
	  inside = bullets[i].draw(g) || inside;
	}
      if(!inside)
	{
	  int x0 = 10 + (int)(Math.random() * (double)(_xmax - 20));
	  int y0 = _ymax - (int)(Math.random() * 20.0f);

	  double dx0 = (Math.random() - 0.5f) * 2.0f;
	  double dy0 = -(Math.random() * 6);

	  for(i = 0; i < _nof_bullets; i++)
	    {
	      bullets[i].clearall(g);
	    }

	  reinit(x0, y0, dx0, dy0);
	}
    }
}

public class fireworks extends java.applet.Applet implements Runnable {
  int nof_rockets = 3;
  int delay       = 50;
  int nof_points  = 20;
  int trail       = 10;
  int dotsize     = 2;
  int life_len    = 100;
  double g        = 0.0f;

  Thread kicker    = null;
  Rocket rockets[] = null;
  int clear        = 0;
  boolean first    = true;

  Color bg         = Color.black;

  Color parseCol(String s)
    {
      if(s.length() >= 6)
	{
	  String sr = s.substring(0, 2);
	  String sg = s.substring(2, 4);
	  String sb = s.substring(4, 6);
	  int r, g, b;

	  try {
	    r = Integer.parseInt(sr, 16);
	  } catch(Exception e) {
	    r = 0;
	  }
	  try {
	    g = Integer.parseInt(sg, 16);
	  } catch(Exception e) {
	    g = 0;
	  }
	  try {
	    b = Integer.parseInt(sb, 16);
	  } catch(Exception e) {
	    b = 0;
	  }
	  return new java.awt.Color(r, g, b);
	}
      return new java.awt.Color(128, 128, 128);
    }

  public void init() {
    int i;

    try {
      nof_rockets = Integer.parseInt(getParameter("ROCKETS"));
    } catch(Exception e) {
      nof_rockets = 3;
    }
    try {
      delay = Integer.parseInt(getParameter("DELAY"));
    } catch(Exception e) {
      delay = 50;
    }
    try {
      trail = Integer.parseInt(getParameter("TRAIL"));
    } catch(Exception e) {
      trail = 10;
    }
    try {
      life_len = Integer.parseInt(getParameter("LIFELENGTH"));
    } catch(Exception e) {
      life_len = 100;
    }
    try {
      nof_points = Integer.parseInt(getParameter("POINTS"));
    } catch(Exception e) {
      nof_points = 3;
    }
    try {
      dotsize = Integer.parseInt(getParameter("POINTSIZE"));
    } catch(Exception e) {
      dotsize = 2;
    }
    try {
      g = (double)Integer.parseInt(getParameter("GRAV")) / 1000.0f;
    } catch(Exception e) {
      g = .01f;
    }
    String bgcol = getParameter("COLOR");

    if(bgcol != null)
      {
	bg = parseCol(bgcol);
      }



    rockets = new Rocket[nof_rockets];

    initrockets();

  }

  void initrockets()
    {
      int i;
      for(i = 0; i < nof_rockets; i++)
	{
	int x = 10 + (int)(Math.random() * (double)(size().width - 20));
	int y = size().height - (int)(Math.random() * 20.0f);

	double dx = (Math.random() - 0.5f) * 2.0f;
	double dy = -(Math.random() * 6);

	Color fg ;

	double cs = Math.random();

	if(cs < .33f)
	  {
 	    fg = new java.awt.Color(155 + (int)(Math.random() * 100),
			  10, 10);
	  }
	else if(cs < .66f)
	  {
 	    fg = new java.awt.Color(155 + (int)(Math.random() * 100),
			  155 + (int)(Math.random() * 100),
			  10);
	  }
	else
	  {
 	    fg = new java.awt.Color(10, 10,
			  155 + (int)(Math.random() * 100));
	  }
	rockets[i] = new Rocket(nof_points, x, y, dx, dy, g,
				dotsize, size().width, size().height, trail, life_len,
				fg,
				bg);
      }
    }

  public void paint(Graphics g) {
      int i;

      g.setColor(Color.white);
      g.drawRect(0, 0, size().width - 1, size().height - 1);
      g.setColor(bg);
      g.fillRect(0, 0, size().width - 1, size().height - 1);

      g.clipRect(2, 2, size().width - 4, size().height - 4);

      for(i = 0; i < nof_rockets; i++)
	{
	  rockets[i].draw(g);
	}
  }


  public void update(Graphics g)
    {
      int i;

      if(clear == 0)
	{
	  g.clipRect(2, 2, size().width - 4, size().height - 4);
	  for(i = 0; i < nof_rockets; i++)
	    {
	      rockets[i].update(g);
	    }
	} else {
	  g.clearRect(0, 0, size().width, size().height);
	  paint(g);
	  clear = 0;
	}
    }

  public boolean mouseDown(java.awt.Event evt, int x, int y)
    {
      clear = 1;
      initrockets();
      return true;
    }

  public void run()
    {
      int dx = 1;

      while(kicker != null) {

	repaint();

	try {
	  Thread.sleep(delay);
	} catch(Exception e) {
	  ;
	}
      }
    }

  public void start()
    {
      if(kicker == null)
	{
	  kicker = new Thread(this);
	  kicker.setPriority(kicker.MIN_PRIORITY);
	  kicker.start();
	}
    }
  public void stop()
    {
      kicker = null;
    }

}