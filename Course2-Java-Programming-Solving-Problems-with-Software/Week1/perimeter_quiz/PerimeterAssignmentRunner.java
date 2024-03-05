import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        // Put code here
        return 0;
    }

    public double getAverageLength(Shape s) {
        long n = s.getPoints().spliterator().getExactSizeIfKnown();

        return this.getPerimeter(s) / n;
    }

    public double getLargestSide(Shape s) {
        double ans = 0.0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            ans = Math.max(currDist, ans);
            prevPt = currPt;
        }

        return ans;
    }

    public double getLargestX(Shape s) {
        // Put code here
        return 0.0;
    }

    public double getLargestPerimeterMultipleFiles(Shape[] shapes) {
        double ans = 0;
        for (Shape shape: shapes) {
            double perim = this.getPerimeter(shape);
            ans = Math.max(perim, ans);
        }
        
        return ans;
    }

    public String getFileWithLargestPerimeter() {
        String ans = "";
        double max = 0;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape shape = new Shape(fr);
            double perim = getPerimeter(shape);
            if (perim > max) {
                max = perim;
                ans = f.getName();
            }
        }
        
        return ans;
    }

    public void testPerimeter () {
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape shape = new Shape(fr);
            double length = getPerimeter(shape);
            System.out.println("filename = " + f.getName() + ", perimeter = " + length);
        }
    }
    
    public void testPerimeterMultipleFiles(int n) {
        Shape[] shapes = getShapes(n);
        double ans = getLargestPerimeterMultipleFiles(shapes);
        System.out.println("largest perimeter = " + ans);
    }

    public void testFileWithLargestPerimeter() {
        String ans = getFileWithLargestPerimeter();
        System.out.println("largest perimeter filename = " + ans);
    }
    
    private Shape[] getShapes(int n) {
        Shape[] shapes = new Shape[n];
        for (int i = 0; i < n; i++) {
            FileResource fr = new FileResource();
            shapes[i] = new Shape(fr);
        }
        
        return shapes;
    }
    
    public void testAverageLength(FileResource fr) {
        Shape s = new Shape(fr);
        double length = getAverageLength(s);
        System.out.println("average length = " + length);
    }
    
    public void testLargestSide(FileResource fr) {
        Shape s = new Shape(fr);
        double length = getLargestSide(s);
        System.out.println("largest side = " + length);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeter();
    }
}
