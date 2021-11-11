# MiniCAD

MiniCAD is a simple drawing tool. It acts like a CAD (Computer Aided Design) program, but not full-featured. 
Lines, circles, rectangles and text fields are the only graphics that can be drawn. 
A graphic can be selected, dragged and resized, and the properties like line width and line colour can be manipulated. 
What's more, the working area can be saved and loaded.  

![Demo](https://raw.githubusercontent.com/RalXYZ/repo-pictures/main/MiniCAD/demo.png)

## Build

Execute this command in the root directory of the project:  

```shell
mvn
```

## Usage

1. Graphic drawing
    - Select a graphic to be drawn by clicking the button on the toolbar
    - If the graphic to be drawn is a text field, then the text needs to be filled into the pop-up dialog box 
    - Click, drag and release to draw a graphic
2. Graphic modifying
    - Click on a graphic to select it
    - Select a colour from colour bar to change the line colour
    - Resize up and down by pressing `+` or `-` 
    - Change the line width by pressing `<` or `>`
    - Remove a graphic by pressing `r`
3. Working area saving and loading
    - Save the working area by selecting `Save` from the menu bar
    - Load the working area by pressing `Open` from the menu bar

## Architecture

### Swing Components

A JFrame is used as the main window, which contains four JPanels: `Canvas`, `ColorBar`, `MenuBar` and `ToolBar`. 
Since the JFrame uses BorderLayout, these JPanels are fixed at a specific position.
- `Canvas` is placed at the centre, where user can draw graphics. 
- `ColorBar` is placed at south. Using `GridLayout`, this JPanel contains coloured buttons, from which users can select the line colour. 
- `MenuBar` is at the north of JFrame. It is a JMenu, where user can load and store files. 
- `ToolBar` is at the west of JFrame. The drawing tools can be selected from this JPanel.  

### Self-defined Graphics

All the graphics: line, circle, rectangle and text field, are extended from an abstract class called `Graphic`. 

```java
public abstract class Graphic implements Serializable {
    public Point src;
    public Point dest;
    public Color color = ColorBar.currentColor;
    public float lineWidth = Define.DEFAULT_LINE_WIDTH;
    
    public abstract boolean canSelect(Point p);

    /* implementation of the methods listed below are omitted in current document for simplicity */
    public void draw(Graphics2D g);
    public void scaleUp();
    public void scaleDown();
    public void lineWidthUp();
    public void lineWidthDown();
    public void move(int x, int y);
}
```
All graphics has *colour* and *line width* property. 
Meanwhile, almost all graphics have a logical `src` and `dest`. 
- It is the source and destination vertex of a **line segment**. 
- It is the upper-left and lower-right vertex of a **rectangle**. 
- It is the upper-left and lower-right vertex of an outer tangent rectangle of an **ellipse**. 
- **Text filed** is positioned by `src` and font size.  

Furthermore, there are also various methods that can be shared though all graphics. 
Scaling and moving the graphics only needs to change the `src` and `dest` vertex. 
Modifying line width and colours are in fact the modification of the members.  

However, some features are more complex. 
Drawing is graphic-specific. Graphics are drawn by using different methods in `Graphics2D`. 
Selecting a graphic is the hardest, because it requires judging whether a point drops in a convex shape. 
Some simple judgements can be done by methods in `Graphics2D`.  

However, the judgements related to ellipse are coded by myself. 
The idea is based on an inequality in geometry, that for a point $P$, it must locate in an ellipse iff:  

$$
| PF_1 | + | PF_2 | \leq 2a
$$

Where $F_1$ and $F_2$ are the two focuses, and $2a$ is the length of major axis.  
The detailed implementation in Java is shown below.  

```java
public boolean canSelect(Point p) {
    final double a = (dest.x - src.x) / 2d;
    final double b = (dest.y - src.y) / 2d;
    final double c = Math.sqrt(Math.abs(Math.pow(a, 2) - Math.pow(b, 2)));
    double f1x, f1y, f2x, f2y;
    if (a > b) {
        f1x = src.x + a - c;
        f2x = src.x + a + c;
        f1y = src.y + b;
        f2y = src.y + b;
    } else {
        f1x = src.x + a;
        f2x = src.x + a;
        f1y = src.y + b - c;
        f2y = src.y + b + c;
    }
    return Math.sqrt(Math.pow(p.x - f1x, 2) + Math.pow(p.y - f1y, 2))
            + Math.sqrt(Math.pow(p.x - f2x, 2) + Math.pow(p.y - f2y, 2)) <= 2 * a;
}
```


