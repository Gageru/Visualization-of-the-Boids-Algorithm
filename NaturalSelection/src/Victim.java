


public class Victim implements Cloneable {

    protected Vector pos = new Vector();
    protected Vector speed = new Vector();


    public Victim() {

        this.iniBird();

    }



    public void iniBird() {
        this.setPos(new Vector(Math.random() * Window.panelWidth(), Math.random() * Window.panelHeight()));
        boolean positiveX = (Math.random() >= 0.5);
        double speedX = Math.random() * (Flock.vMax - Flock.vMin) + Flock.vMin;
        if (!positiveX)
            speedX = -speedX;
        boolean positiveY = (Math.random() >= 0.5);
        double speedY = Math.random() * (Flock.vMax - Flock.vMin) + Flock.vMin;
        if (!positiveY)
            speedY = -speedY;
        this.setSpeed(new Vector(speedX, speedY));
    }

    public void deleteBird(){
        this.setPos(new Vector(1000, 1000));
    }



    public Vector distance(Victim b) {
        return this.pos().add(b.pos().multiply(-1));
    }

    public boolean compare(Victim b) {
        if (b.pos().equals(this.pos()) && b.speed().equal(this.speed))
            return true;
        else
            return false;
    }

    @Override
    public Victim clone() {
        Victim bird = null;
        try {
            bird = (Victim)super.clone();
            bird.pos = pos.clone();
            bird.speed = speed.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
        }
        return bird;
    }


    public static Vector averageSpeed(Victim[] birds) {
        Vector[] speeds = new Vector[birds.length];
        for (int i = 0 ; i < birds.length ; i++) {
            speeds[i] = birds[i].speed().clone();
        }
        return Vector.average(speeds);
    }


    public static Vector averagePos(Victim[] birds) {
        Vector[] pos = new Vector[birds.length];
        for (int i = 0 ; i < birds.length ; i++) {
            pos[i] = birds[i].pos().clone();
        }
        return Vector.average(pos);
    }


    public Vector pos() {
        return this.pos;
    }

    public Vector speed() {
        return this.speed;
    }

    //Setters

    public void setPos (Vector newPos) {

        if (newPos.x() < Window.panelWidth() && newPos.x() > -Flock.birdRadius())
            this.pos.setX(newPos.x());
        else if (newPos.x() >= Window.panelWidth())
            this.pos.setX(-Flock.birdRadius() + 1);
        else if (newPos.x() <= -Flock.birdRadius())
            this.pos.setX(Window.panelWidth() - 1);

        if (newPos.y() < Window.panelHeight() && newPos.y() > -Flock.birdRadius())
            this.pos.setY(newPos.y());
        else if (newPos.y() >= Window.panelHeight())
            this.pos.setY(-Flock.birdRadius() + 1);
        else if (newPos.y() <= -Flock.birdRadius())
            this.pos.setY(Window.panelHeight() - 1);

    }

    public void setSpeed (Vector newSpeed) {

        if (newSpeed.x() > Flock.vMax || newSpeed.x() < -Flock.vMax) {
            this.speed.setX(Flock.vMax);
        }
        else {
            this.speed.setX(newSpeed.x());
        }

        if (newSpeed.y() > Flock.vMax || newSpeed.y() < -Flock.vMax) {
            this.speed.setY(Flock.vMax);
        }
        else {
            this.speed.setY(newSpeed.y());
        }


    }


}