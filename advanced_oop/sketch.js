//Global vars for width and height so we don't have 
//To nest our object in setup
var gloWidth = 1850;
var gloHeight = 950;

//initial diameter for objec
var beginDia = 5;

var flagLeave = false;
var lastCollide = 0;

var allAsteroids = [];

var globalState = 0;

//Main object, just a circle with other attributes
function Asteroid() {
    this.x = Math.random() * gloWidth;
    this.y = Math.random() * gloHeight;
    this.dia = beginDia;
    this.xVel =  (Math.random() * 2) - 1;
    this.yVel = (Math.random() * 2) - 1;
    this.color = '#d65d0e';

    this.update = function() {
        if (this.x > gloWidth || this.x < 0) {
            this.xVel = -this.xVel;
        }
        if (this.y > gloHeight || this.y < 0) {
            this.yVel = -this.yVel;
        }
        this.x += this.xVel;
        this.y += this.yVel;
    }

    this.display = function() {
        push();
        fill(this.color);
        ellipse(this.x, this.y, this.dia, this.dia);
        pop();
    }

}

function setup() {
    createCanvas(gloWidth, gloHeight);
    background('#fbf1c7');
    fill('#d65d0e');

    //Create a bunch of new objects
    for (var i = 0; i < 1000; i++) {
        allAsteroids.push(new Asteroid());
    }
    
    //Display our objects
    for (var i = 0; i < allAsteroids.length; i++) {
        allAsteroids[i].display();
    }
}

function draw() {
    background('#fbf1c7');

    //increment our race condition
    lastCollide += 1;

    //Main method for checkout collisions
    for (var i = 0; i < allAsteroids.length; i++) {

        //grab one particle
        var particle1 = allAsteroids[i];
        for(var j = 0; j < allAsteroids.length; j++) {
            
            //Grab second particle
            var particle2 = allAsteroids[j];

            //If we are not looking at the same particle
            if (i != j) {

                //Calculate distance
                var distance = dist(particle1.x, particle1.y, particle2.x, particle2.y); 

                //If we are close enough to other partice
                if (distance < (particle1.dia / 2) + (particle2.dia / 2)) {
                    

                    //remove first particle to delete
                    var toDel = allAsteroids.splice(i, 1);

                    //Take care of issue of first removed particle
                    //So we do not accidently delete second particle
                    //incorrectly
                    if (i < j) {
                        var toDel2 = allAsteroids.splice(j - 1, 1);
                    } else {
                        var toDel2 = allAsteroids.splice(j + 1, 1);
                    }

                    //Create new particle to exist as addition of first 2
                    var bigger = new Asteroid();
                    bigger.x = particle1.x;
                    bigger.y = particle1.y;
                    bigger.xVel = particle1.xVel + particle2.xVel;
                    bigger.yVel = particle1.yVel + particle2.yVel;       

                    //Calculate new surface area
                    var surf1 = PI * ((particle1.dia / 2) * (particle1.dia / 2));
                    var surf2 = PI * ((particle2.dia / 2) * (particle2.dia / 2));
                    var resSurf = surf1 + surf2;
                    var resDia = Math.sqrt(resSurf / PI) * 2;
                    bigger.dia = resDia; 
                    
                    //Delete the 2 old particles
                    delete toDel;
                    delete toDel2;
                    
                    //add new object
                    allAsteroids.push(bigger);

                    //If particles combine get a new color for new particle
                    bigger.color = getFill();
                    bigger.display();

                    //If we combined, signal method to exit to 
                    //avoid concurrent modification errors
                    flagLeave = true;

                    //reset last collision timer
                    lastCollide = 0;
                    break;
                }
            }
        }
        //If we are signaled to leave, continue the exit
        if (flagLeave) break;
    }
    //Reset leave flag
    flagLeave = false;
    //increment last collision time
    lastCollide += 1;

    //Update method
    for (var i = 0; i < allAsteroids.length; i++) {
        //RACE CONDITION
        //If we haven't collided recently, make circles bigger
        //in order to increase chances of colliding
        if (lastCollide > 30) {
            allAsteroids[i].dia += 1;
        }
        allAsteroids[i].update();
        allAsteroids[i].display();
    } 

    //If there is only one particle left then game is over, signal it
    if (allAsteroids.length == 1) {
        textSize(256);
        text('GAME OVER!', 0, height / 2, width, height);
        lastCollide = 0;
    }

}

//Get a new hex color string for fill, simple switch
//Based on current color state
function getFill() {
    var color = '';
    globalState == 6 ? globalState = 0 : globalState += 1;
    switch (globalState) {
        case 0:
            color = '#fb4934';
            break;
        case 1:
            color = '#b8bb26';
            break;
        case 2:
            color = '#fabd2f';
            break;
        case 3:
            color = '#83a598';
            break;
        case 4:
            color = '#d3869b';
            break;
        case 5:
            color = '#8ec07c';
            break;
        case 6:
            color = '#fe8019';
            break;
    }
    return color;
}
