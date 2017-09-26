/* MAIN FEATURE IS CLICKS HOWEVER YOU CAN MOVE PROGRAM WITH ARROWS */
var gloSize = 30;
var circleState = 0;
var globalState = 0;
var clicked = false;

//Hold onto all our current circles colors as simple strings
var curLarge, curMed, curSmall;

var allObjs = [];

//Main object for our program, a triple layered circled
function circleObj(xLoc, yLoc) {
    //Intake x and y oords
    this.x = xLoc;
    this.y = yLoc;

    //initialize circle sizes to go from largest to smallest
    this.largeCirc = gloSize;
    this.medCirc = (gloSize / 3) * 2;
    this.smallCirc = gloSize / 3;

    //Over arching display function
    this.display = function() {
        ellipseMode(CENTER);

        //Push all our fills so we only change one circle color

        /*
            Overall Idea is one circle will change color at a time
            Based on mouseClicks we will shift which circle changes
            as well as the color that it will change to

            We are constantly holding on to each circles current
            color so we can reference the fill in the future

            The getFill method will assign a string value to
            the current circle to be changed's fill variable
        */

        push();

        //If we want to change the fill get a new fill
        if (circleState == 0) curLarge = getFill();

        //Assign fill to our current held string color variable
        fill(curLarge);
        
        //Draw the ellipse
        ellipse(this.x, this.y, this.largeCirc, this.largeCirc);              
        pop();
        
        //Same as above
        push();
        if (circleState == 1) curMed = getFill();
        fill(curMed);
        ellipse(this.x, this.y, this.medCirc, this.medCirc);
        pop();
        
        //Same as above
        push();
        if (circleState == 2) curSmall = getFill();
        fill(curSmall);
        ellipse(this.x, this.y, this.smallCirc, this.smallCirc);
        pop();
    }
}

function setup() {
    createCanvas(500, 500);
    background('#fbf1c7');
    stroke('#fbf1c7');
    fill('#cc241d');

    //Assign all our string color variables
    curLarge = '#cc241d';
    curMed = '#cc241d';
    curSmall = '#cc241d';

    rectMode(CENTER);
    
    //Hold onto current x and Y to be set for objs
    currY = gloSize + 5;
    currX = gloSize + 5;

    //169 objs fill canvas create all of them
    for (var i = 0; i < 169; i++) {
        allObjs.push(new circleObj(currX, currY));

        //Shift down Y
        currY += gloSize + 5;

        //If we are at the bottom of the canvas
        if (currY > height - (gloSize + 5)) {

            //Reset Y
            currY = gloSize + 5;

            //Shift X over one
            currX += gloSize + 5;
        }
    }

    //Call display function for each
    for (var i = 0; i < allObjs.length; i++) {
        allObjs[i].display();
    }
}

function draw() {
    //Only Re-Draw if clicked
    if (clicked) {
        //Re draw all
        for (var i = 0; i < allObjs.length; i++){
            allObjs[i].display();
        }

        //Reset clicked flag
        clicked = false;
    }
}

//If mouseClicked
function mouseClicked() {
    //Shift each state, if we hit our max reset to 0
    circleState == 2 ? circleState = 0 : circleState += 1;
    globalState == 6 ? globalState = 0 : globalState += 1;

    //Flag clicked to be true
    clicked = true;
}

//Get a new hex color string for fill, simple switch
//Based on current color state
function getFill() {
    var color = '';
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

//If keys are pressed we move all the objects around
//I would like to implement a reset feature in the future however
//I could not get it working as of working on this
function keyPressed() {
    var x = 0;
    var y = 0;
    if (keyCode == LEFT_ARROW) {
        x = -1;
    } else if (keyCode == RIGHT_ARROW) {
        x = 1;
    } else if (keyCode == DOWN_ARROW) {
        y = 1;
    } else if (keyCode == UP_ARROW) {
        y = -1;
    }
    background('#fbf1c7');
    for (var i = 0; i < allObjs.length; i++){
        allObjs[i].x += x;
        allObjs[i].y += y;
        allObjs[i].display();
    }
}

