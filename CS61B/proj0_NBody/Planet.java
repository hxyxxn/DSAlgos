import java.math.BigDecimal;

public class Planet {
    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        // Distances and mass are in meters and kilograms, respectively.
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

// Makes a copy of the passed in object (copies the values of all the fields into a new object)
    public Planet(Planet planet) {
        this.xxPos = planet.xxPos;
        this.yyPos = planet.yyPos;
        this.xxVel = planet.xxVel;
        this.yyVel = planet.yyVel;
        this.mass = planet.mass;
        this.imgFileName = planet.imgFileName;
    }

    public void update(double dt, double fX, double fY) {
        double dVelX, dVelY;
        // F = a * m, thus a = F / m.
        // dt = delta time

        dVelX = (fX / this.mass) * dt;
        dVelY = (fY / this.mass) * dt;

        this.xxVel += dVelX;
        this.yyVel += dVelY;

        this.xxPos += this.xxVel * dt;
        this.yyPos += this.yyVel * dt;
    }


    // Calculates the distance between the 2 planets.
    public double calcDistance(Planet otherPlanet) {
        double deltaX, deltaX_squared, deltaY, deltaY_squared, r_squared, r;
        deltaX = this.xxPos - otherPlanet.xxPos;
        deltaX_squared = deltaX*deltaX;
        deltaY = this.yyPos - otherPlanet.yyPos;
        deltaY_squared = deltaY*deltaY;

        r_squared = deltaX_squared + deltaY_squared;
        r = Math.sqrt(r_squared);
        return r;
    }

    // Calculates the gravitational exerted on the Planet passed in as an argument.
    public double calcForceExertedBy(Planet otherPlanet) {
        double G, gravForce, distance;
        G = 6.67e-11;

        // F = G * m1 * m2  / r_squared
        distance = this.calcDistance(otherPlanet);
        gravForce = (G*this.mass*otherPlanet.mass) / (distance*distance);
        return gravForce;
    }

    // Force exerted onto the passed in Planet along the x-axis.
    public double calcForceExertedByX(Planet otherPlanet) {
        if (this == otherPlanet) 
            { return 0; }
        double force, xForce, distance, xDistance;
        distance = this.calcDistance(otherPlanet);
        xDistance = otherPlanet.xxPos - this.xxPos;
        force = this.calcForceExertedBy(otherPlanet);

        // Force x-axis = (Total force * delta X) / total distance
        xForce = (force*xDistance) / distance;
        return xForce;
    }

    public double calcForceExertedByY(Planet otherPlanet) {
        if (this == otherPlanet) 
            { return 0; }
        double force, yForce, distance, yDistance;
        distance = this.calcDistance(otherPlanet);
        yDistance = otherPlanet.yyPos - this.yyPos;
        force = this.calcForceExertedBy(otherPlanet);

        // Force y-axis = (Total force * delta Y) / total distance
        yForce = (force*yDistance) / distance;
        return yForce;
    }

    public double calcNetForceExertedByX(Planet[] planets) {
        BigDecimal netForce = new BigDecimal("0.0");
        for (Planet planet : planets) {
            // netForce = this.calcForceExertedByX(planet);
            netForce = netForce.add(BigDecimal.valueOf(this.calcForceExertedByX(planet)));
        }
        return netForce.doubleValue();
    }

    public double calcNetForceExertedByY(Planet[] planets) {
        BigDecimal netForce = new BigDecimal("0.0");
        for (Planet planet : planets) {
            netForce = netForce.add(BigDecimal.valueOf(this.calcForceExertedByY(planet)));
        }
        return netForce.doubleValue();
    }

    public String toString() {
        return "Name: " + this.imgFileName + " mass: " + this.mass;
    }

    public void draw() {
        StdDraw.picture(this.xxPos, this.yyPos, "images/" + this.imgFileName);
    }

    public static void main(String[] args) {
        Planet Saturn = new Planet(2.3e12, 9.5e11, 0, 0, 6.0e26, "");
        Planet Sun = new Planet(1.0e12, 2.0e11, 0, 0, 2.0e30, "");
        Planet samh = new Planet(1, 0, 0, 0, 10, "");
        Planet AEgir = new Planet(3, 3, 0, 0, 5, "");
        Planet Rocinante = new Planet(5, -3, 0, 0, 50, "");

        Planet squirrel = new Planet(0, 0, 3, 5, 1, "");


        System.out.println("The distance between Saturn and the Sun = " + Saturn.calcDistance(Sun) + " metres.");
        System.out.println("The force exerted on the Sun by Saturn = " + Saturn.calcForceExertedBy(Sun) + " metres.");
        System.out.println("The force exerted along the x-axis on the Sun by Saturn = " + Saturn.calcForceExertedByX(Sun) + " metres.");
        System.out.println("The force exerted along the y-axis on the Sun by Saturn = " + Saturn.calcForceExertedByY(Sun) + " metres.");
        System.out.println(Sun.calcForceExertedByX(Saturn));
        System.out.println(Sun.calcForceExertedByY(Saturn));
        System.out.println(Sun.calcForceExertedByX(Sun));
        /** @TODO: The below statement returns a negative value, it should be positive. */
        System.out.println(samh.calcForceExertedBy(Rocinante));
        System.out.println(samh.calcForceExertedByX(Rocinante));

        System.out.println(squirrel.xxVel + " " + squirrel.yyVel);
        squirrel.update(1, -5, -2);
        System.out.println(squirrel.xxVel + " " + squirrel.yyVel);
    }
}
