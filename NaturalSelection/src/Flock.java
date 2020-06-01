
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//Collective behaviour
public class Flock extends JPanel {
    //Treangle
    private static double x1, x2, x3, x4, y1, y2, y3, y4, r1, r2, d, b, a, h, x, y;
    private static int[] X1;
    private static int[] Y1;
    //Birds
    protected int nbBirds = 800; //300
    protected Victim[] birds = new Victim[this.nbBirds];
    protected static double birdRadius = 18; //Graphical size

    //Obstacles
    protected int nbObstacles = 0;
    List<Obstacle> obstacles = new ArrayList<Obstacle>();
    protected static double obstacleRadius = 10;

    //Predators
    protected int nbPredators = 0;
    List<Predator> predators = new ArrayList<Predator>();
    protected static double predatorRadius = 10; //Graphical size

    //Speed
    protected static double vMin = 1;
    protected static double vMax = 4;
    protected static double vMaxPredator = 10;

    //Radius
    protected static double neighborCohesionRadius = 50;
    protected static double neighborSeparationRadius = 30;
    protected static double neighborAlignmentRadius = 45;
    protected static double neighborObstacleRadius = 50;
    protected static double neighborPredatorChaseRadius = 1000;
    protected static double neighborPredatorFleeRadius = 50;

    //Weight for the different forces
    protected static double separationWeightIni = 4;
    protected static double alignmentWeightIni = 0.05;
    protected static double cohesionWeightIni = 0.3;

    protected static double separationWeight = Flock.separationWeightIni;
    protected static double alignmentWeight = Flock.alignmentWeightIni;
    protected static double cohesionWeight = Flock.cohesionWeightIni;

    protected static double obstacleWeight = 3.5;
    protected static double predatorWeight = 3;
    protected static double predatorChaseWeight = 3.5;


    public Flock() {
        creationBirds();
        creationObstacles();
        creationPredators();
    }


    public void paintComponent(Graphics g) {

        //Reset
        super.paintComponent(g);

        //Отрисовка препятствий
        g.setColor(Color.BLUE);
        for (Obstacle obstacle : obstacles) {
            g.fillOval((int) obstacle.pos().x(), (int) obstacle.pos().y(), (int) Flock.obstacleRadius(), (int) Flock.obstacleRadius());
        }

        //Отрисовка хищников и добычи
        display(this.birds, g, Color.decode("#269926"));
        display(this.predators.toArray(new Predator[nbPredators]), g, Color.RED);

    }
    /*public static void  TreanglePaint(Victim bird){
        double x = bird.pos().x() + Flock.birdRadius()/2;
        double y = bird.pos().y() + Flock.birdRadius()/2;
        double dx =  bird.pos().x() + Flock.birdRadius()/2 + bird.speed().x() / bird.speed().norm() * Flock.birdRadius();
        double dy = bird.pos().y() + Flock.birdRadius()/2 + bird.speed().y() / bird.speed().norm() * Flock.birdRadius();
        double k , k1 ,k2;

        if (dx > x && dy > y) {
            k = (dx - x) / (dy - y);
            k1 = -1;
            k2 = k;

            int X []= {(int)dx,(int)(x-k1),(int)(x+k1)};
            int Y []= {(int)dy,(int)(y+k2),(int)(y-k2)};
            X1 =X ;
            Y1=Y;
        }

        if (dx > x && dy < y) {
            k = (dx - x) / (y - dy);
            k1 = -1;
            k2 = k;

            int X []= {(int)dx,(int)(x-k1),(int)(x+k1)};
            int Y []= {(int)dy,(int)(y-k2),(int)(y+k2)};
            X1 =X ;
            Y1=Y;
        }

        if (dx < x && dy < y) {
            k = (x - dx) / (y - dy);
            k1 = -1;
            k2 = k;

            int X []= {(int)dx,(int)(x-k1),(int)(x+k1)};
            int Y []= {(int)dy,(int)(y+k2),(int)(y-k2)};
            X1 =X ;
            Y1=Y;
        }

        if (dx < x && dy > y) {
            k = (x - dx) / (dy - y);
            k1 = -1;
            k2 = k;

            int X []= {(int)dx,(int)(x-k1),(int)(x+k1)};
            int Y []= {(int)dy,(int)(y-k2),(int)(y+k2)};
            X1 =X ;
            Y1=Y;
        }

    }*/
    static double sqr(double a){
        double result = a*a;
        return result;
    }

    public static void  treanglePaint (Victim bird){
        x1 = bird.pos().x() + Flock.birdRadius()/2;
        y1 = bird.pos().y() + Flock.birdRadius()/2;
        x2 =  bird.pos().x() + Flock.birdRadius()/2 + bird.speed().x() / bird.speed().norm() * Flock.birdRadius();
        y2 = bird.pos().y() + Flock.birdRadius()/2 + bird.speed().y() / bird.speed().norm() * Flock.birdRadius();
        r1 = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) * Math.tan(Math.PI/8);
        r2 = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2)) / Math.cos(Math.PI/8);
        d = Math.sqrt(sqr(x1-x2)+sqr(y1-y2));
        b =(sqr(r2)-sqr(r1)+sqr(d))/(2*d);
        a = d - b;
        h= Math.sqrt(sqr(r2)-sqr(b));
        x= x1+(x2-x1)/(d/a);
        y= y1+(y2-y1)/(d/a);
        x3=x-(y-y2)*h/b;
        y3=y+(x-x2)*h/b;
        x4=x+(y-y2)*h/b;
        y4=y-(x-x2)*h/b;
        int X []= {(int)x2,(int)(x3),(int)(x4)};
        int Y []= {(int)y2,(int)(y3),(int)(y4)};
        X1 =X ;
        Y1=Y;
    }
    /*public static void  TreanglePaint (Victim bird){
        double x1 = bird.pos().x() + Flock.birdRadius()/2;
        double y1 = bird.pos().y() + Flock.birdRadius()/2;
        double x2 =  bird.pos().x() + Flock.birdRadius()/2 + bird.speed().x() / bird.speed().norm() * Flock.birdRadius();
        double y2 = bird.pos().y() + Flock.birdRadius()/2 + bird.speed().y() / bird.speed().norm() * Flock.birdRadius();
        double b = Math.sqrt(Math.pow(x2-x1,2)+Math.pow(y2-y1,2));
        double c = b / Math.cos(Math.PI/6);
        double a = b * Math.tan(Math.PI/6);
        double x3 = (1/2)*((y1-y2)*Math.sqrt(-(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(-c+a-y1+y2)*(-c+a+y1-y2))*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(c+a-y1+y2)*(c+a+y1-y2))*Flock.square(x1-x2))+(Math.pow(x1,3)-Flock.square(x1)*x2+(Flock.square(y2)-2*y1*y2-Flock.square(c)+Flock.square(y1)+Flock.square(a)-Flock.square(x2))*x1-x2*(Flock.square(a)-Flock.square(c)-Flock.square(x2)-Flock.square(y2)+2*y1*y2-Flock.square(y1)))*(x1-x2))/((x1-x2)*(Flock.square(x1)-2*x2*x1+Flock.square(x2)+Flock.square(y1-y2)));
        System.out.println((1/2)*((y1-y2)*Math.sqrt(-(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(-c+a-y1+y2)*(-c+a+y1-y2))*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(c+a-y1+y2)*(c+a+y1-y2))*Flock.square(x1-x2))+(Math.pow(x1,3)-Flock.square(x1)*x2+(Flock.square(y2)-2*y1*y2-Flock.square(c)+Flock.square(y1)+Flock.square(a)-Flock.square(x2))*x1-x2*(Flock.square(a)-Flock.square(c)-Flock.square(x2)-Flock.square(y2)+2*y1*y2-Flock.square(y1)))*(x1-x2)));
        double y3 = (-Math.sqrt(-(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(-c+a-y1+y2)*(-c+a+y1-y2))*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(c+a-y1+y2)*(c+a+y1-y2))*Flock.square(x1-x2))+Math.pow(y1,3)-Flock.square(y1)*y2+(Flock.square(a)+Flock.square(x1)-Flock.square(c)+Flock.square(x2)-2*x2*x1-Flock.square(y2))*y1+Math.pow(y2,3)+(Flock.square(x2)-2*x2*x1+Flock.square(c)-Flock.square(a)+Flock.square(x1))*y2)/(2*Flock.square(y1)-4*y1*y2+2*Flock.square(y2)+2*Flock.square(x1-x2));
       // System.out.println(y3);
        double x4 = (1/2)*((-y1+y2)*Math.sqrt(-(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(-c+a-y1+y2)*(-c+a+y1-y2))*Flock.square(x1-x2)*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(c+a-y1+y2)*(c+a+y1-y2)))+(x1-x2)*(Math.pow(x1,3)-Flock.square(x1)*x2+(Flock.square(y1)-2*y1*y2+Flock.square(y2)+Flock.square(a)-Flock.square(c)-Flock.square(x2))*x1-x2*(-Flock.square(c)-Flock.square(x2)+Flock.square(a)-Flock.square(y1)+2*y1*y2-Flock.square(y2))))/((Flock.square(x1)-2*x2*x1+Flock.square(x2)+Flock.square(y1-y2))*(x1-x2));
        //System.out.println();
        double y4 = (Math.sqrt(-Flock.square(x1-x2)*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(c+a+y1-y2)*(c+a-y1+y2))*(-Flock.square(x1)+2*x2*x1-Flock.square(x2)+(-c+a+y1-y2)*(-c+a-y1+y2)))+Math.pow(y1,3)-Flock.square(y1)*y2+(Flock.square(a)+Flock.square(x1)-Flock.square(c)+Flock.square(x2)-2*x2*x1-Flock.square(y2))*y1+Math.pow(y2,3)+(Flock.square(x2)-2*x2*x1+Flock.square(c)-Flock.square(a)+Flock.square(x1))*y2)/(2*Flock.square(y1)-4*y1*y2+2*Flock.square(y2)+2*Flock.square(x1-x2));
        int X []= {(int)x2,(int)(x3),(int)(x4)};
        int Y []= {(int)y2,(int)(y3),(int)(y4)};
        X1 =X ;
        Y1=Y;
    }*/


    public void display(Victim[] birdsToDisplay, Graphics g, Color c) {
        Victim[] birdsAfterMoving = new Victim[birdsToDisplay.length];
        for (int i = 0 ; i < birdsToDisplay.length ; i++) {
            Victim bird = birdsToDisplay[i];
            g.setColor(c);
            Flock.treanglePaint(bird);
            g.fillPolygon(X1, Y1, 3);
            //если объект хищник => вызываем расчет параметров хищник
            // иначе для добычи
            if (bird instanceof Predator) {
                birdsAfterMoving[i] = (Predator)bird.clone();
                this.movePredator((Predator)birdsAfterMoving[i]);
            }
            else {
                birdsAfterMoving[i] = (Victim)bird.clone();
                this.move(birdsAfterMoving[i]);
            }
        }
        //Устанвливаем новые позиции и скорости объектов
        for (int i = 0 ; i < birdsToDisplay.length ; i++) {
            if (birdsAfterMoving[i] instanceof Predator) {
                this.predators.set(i, (Predator)birdsAfterMoving[i].clone());
            }
            else
                birdsToDisplay[i] = (Victim)birdsAfterMoving[i].clone();
        }
    }



    public void move(Victim b) {

        //Образование стаи
        Victim[] neighborsCohesion = this.neighbors(b, Flock.neighborCohesionRadius);
        Victim[] neighborsSeparation = this.neighbors(b, Flock.neighborSeparationRadius);
        Victim[] neighborsAlignment = this.neighbors(b, Flock.neighborAlignmentRadius);
        //Силы притяжения между птицами
        Vector cohesionForce = calculateCohesionForce(b, neighborsCohesion).multiply(Flock.cohesionWeight);
        Vector separationForce = calculateSeparationForce(b, neighborsSeparation).multiply(Flock.separationWeight);
        Vector alignmentForce = calculateAlignmentForce(b, neighborsAlignment).multiply(Flock.alignmentWeight);
        //Соседние препятствия
        Obstacle[] neighborObstacles = this.neighborObstacles(b);
        Vector obstacleForce = calculateSeparationForce(b, neighborObstacles).multiply(Flock.obstacleWeight);
        //Соседние хищники
        Predator[] neighborPredators = this.neighborPredators(b, Flock.neighborPredatorFleeRadius);
        Vector predatorForce = calculateSeparationPredatorForce(b, neighborPredators).multiply(Flock.predatorWeight);
        //Обновление скорости
        Vector newSpeed = new Vector();
        newSpeed.set(newSpeed.add(obstacleForce));
        if (neighborPredators.length == 0) {
            System.out.println("Chill");
            newSpeed.set(newSpeed.add(b.speed()));
            newSpeed.set(newSpeed.add(cohesionForce));// Привлечение близких птиц
            newSpeed.set(newSpeed.add(separationForce));// Вход в группу
            newSpeed.set(newSpeed.add(alignmentForce));// Выравнивание на близких птицах
        }
        else {
            newSpeed.set(newSpeed.add(predatorForce)); //Определение захвата цели
            System.out.println("Danger");
        }
        b.setSpeed(newSpeed);
        //Обновление позиции
        Vector newPos = new Vector();
        newPos.set(newPos.add(b.pos()));
        newPos.set(newPos.add(newSpeed));
        b.setPos(newPos);
    }


    //Move a predator
    public void movePredator(Predator p) {


        Victim[] neighborBirds = this.neighbors(p, Flock.neighborPredatorChaseRadius);
        Victim[] closestBird = {this.closestBird(p, neighborBirds)};

        Vector cohesionForce = calculateCohesionForce(p, closestBird).multiply(Flock.predatorChaseWeight);

        Obstacle[] neighborObstacles = this.neighborObstacles(p);

        Vector obstacleForce = calculateSeparationForce(p, neighborObstacles).multiply(Flock.obstacleWeight);

        Vector newSpeed = new Vector();
        newSpeed.set(newSpeed.add(cohesionForce));
        newSpeed.set(newSpeed.add(obstacleForce));
        p.setSpeed(newSpeed);

        Vector newPos = new Vector();
        newPos.set(newPos.add(p.pos()));
        newPos.set(newPos.add(p.speed()));
        p.setPos(newPos);

        if (p.distance(closestBird[0]).norm() < Flock.birdRadius)
            kill(closestBird[0]);
    }


    //Расчет разделительно веса
    public Vector calculateCohesionForce(Victim b, Victim[] neighbors) {
        Vector force = new Vector();
        if (neighbors.length > 0) {
            Vector averagePos = Victim.averagePos(neighbors);
            force.set(averagePos.add(b.pos().multiply(-1)));
            force.set(force.normalize());
        }
        return force;
    }

    //Расчет веса сцепления
    public Vector calculateSeparationForce(Victim b, Victim[] neighbors) {
        Vector force = new Vector();
        int n = neighbors.length;
        double[] distance = new double[n];
        for(int i = 0 ; i < n ; i++) {
            distance[i] = b.distance(neighbors[i]).norm();
            if (distance[i] > 0) {
                Vector separation = b.pos().add(neighbors[i].pos().multiply(-1));
                separation.set(separation.normalize());
                separation.set(separation.multiply(1/distance[i]));
                force.set(force.add(separation));
            }
        }
        return force;
    }

    //Расчитывание веса выравнивания
    public Vector calculateAlignmentForce(Victim b, Victim[] neighbors) {
        Vector force = new Vector();
        if (neighbors.length > 0) {
            Vector averageSpeed = Victim.averageSpeed(neighbors);
            force = averageSpeed.add(b.speed().multiply(-1));
            force.set(force.normalize());
        }
        return force;
    }


    public Vector calculateSeparationPredatorForce(Victim b, Victim[] neighbors) {
        Vector force = new Vector();
        if (neighbors.length > 0) {
            Vector averagePos = Victim.averagePos(neighbors);
            force.set(averagePos.add(b.pos().multiply(-1)));
            force.set(force.multiply(-1));
            force.set(force.normalize());
        }
        System.out.println(force.norm());
        return force;
    }



    public Victim[] neighbors(Victim b, double distance) {
        List<Victim> neighbors = new ArrayList<Victim>();
        for (int i = 0 ; i < this.nbBirds ; i++) {
            double distanceNeighbor = b.distance(this.birds[i]).norm();
            if (distanceNeighbor <= distance && distanceNeighbor >= 0) {
                if (!b.compare(this.birds[i])) { //A bird is not a neighbor of itself
                    neighbors.add(this.birds[i]);
                }
            }
        }
        return neighbors.toArray(new Victim[neighbors.size()]);
    }


    public Victim closestBird(Predator p, Victim[] neighbors) {
        if (neighbors.length == 0)
            return null;
        else {
            Victim closestBird = neighbors[0];
            double distance = p.distance(neighbors[0]).norm();
            for (int i = 1 ; i < neighbors.length ; i++) {
                double distanceNext = p.distance(neighbors[i]).norm();
                if (distanceNext < distance) {
                    distance = distanceNext;
                    closestBird = neighbors[i];
                }
            }
            return closestBird;
        }
    }

    public Predator[] neighborPredators(Victim b, double distance) {
        List<Predator> neighbors = new ArrayList<Predator>();
        for (int i = 0 ; i < this.nbPredators ; i++) {
            double distanceNeighbor = b.distance(this.predators.get(i)).norm();
            if (distanceNeighbor <= distance && distanceNeighbor >= 0) {
                neighbors.add(this.predators.get(i));
            }
        }
        return neighbors.toArray(new Predator[neighbors.size()]);
    }

    public Obstacle[] neighborObstacles(Victim b) {
        List<Obstacle> neighbors = new ArrayList<Obstacle>();
        for (int i = 0 ; i < this.nbObstacles ; i++) {
            double distanceNeighbor = b.distance(this.obstacles.get(i)).norm();
            if (distanceNeighbor <= Flock.neighborObstacleRadius && distanceNeighbor >= 0) {
                neighbors.add(this.obstacles.get(i));
            }
        }
        return neighbors.toArray(new Obstacle[neighbors.size()]);
    }

    public void kill(Victim b) {
        b.iniBird();
    }

    public void creationBirds() {
        for (int i = 0 ; i < this.nbBirds ; i++) {
            this.birds[i] = new Victim();
        }
    }


    public void creationObstacles() {
        for (int i = 0 ; i < this.nbObstacles ; i++) {
            this.obstacles.add(new Obstacle());
        }
    }


    public void creationPredators() {
        for (int i = 0 ; i < this.nbPredators ; i++) {
            this.predators.add(new Predator());
        }
    }


    public void addObstacle() {
        this.nbObstacles++;
        this.obstacles.add(new Obstacle());
    }


    public void removeObstacles() {
        this.nbObstacles = 0;
        this.obstacles.clear();
    }


    public void addPredator() {
        this.nbPredators++;
        this.predators.add(new Predator());
    }


    public void removePredators() {
        this.nbPredators = 0;
        this.predators.clear();
    }



    public int nbBirds() {
        return this.nbBirds;
    }

    public Victim[] birds() {
        return this.birds;
    }

    public static double birdRadius() {
        return Flock.birdRadius;
    }

    public static double obstacleRadius() {
        return Flock.obstacleRadius;
    }

    public static double predatorRadius() {
        return Flock.predatorRadius;
    }


    public static void setSeparationWeight(double weight) {
        Flock.separationWeight = weight;
    }

    public static void setCohesionWeight(double weight) {
        Flock.cohesionWeight = weight;
    }

    public static void setAlignmentWeight(double weight) {
        Flock.alignmentWeight = weight;
    }


}