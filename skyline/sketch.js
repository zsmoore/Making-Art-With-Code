state = -1;
rise = true;
setLights = false;
initial = true;
frames = 0;

function setup() {
    createCanvas(1920, 1080);
    background('#7ec0ee');
    noStroke();
}

function draw() {


    //Keep track of frames so we know
    //When to change day state
    frames += 1;

    //Every 50 frames evaluate state
    if (frames == 50) {

        //Initialization edge case band aid
        initial = false;

        //Reset frames for next trigger
        frames = 0;

        //Reset Setlights if set, we needed the flag for all 50 frames
        if (setLights) setLights = false;
        
        //If we are rising changeColor with 
        //a +1 state change
        if (rise) {
            
            changeColor(state += 1);

            //State 4 is our max state then we
            //start to fall
            if (state == 4) {
                rise = false;
            }
        } else {
            //If we are falling use a -1 state change
            changeColor(state -= 1);

            //At -4 night will be over
            if (state == -4) {
                //Flag lights in edge case
                setLights = true;

                //Reset state and rise
                state = 0;
                rise = true;
            }
        }
    }

    //Grass
    fill('#8ec07c');
    rect(0, 300, 1000, 100);
 
    //background building
    fill('#af3a03');
    rect(600, 100, 150, 150);

    //Buildings
    //Conditionally color buildings based on day vs night
    (state < 0 || setLights) && !initial? fill('#3c3836') : fill('#d5c4a1');
    //Main center buildings
    rect(50, 150, 400, 150);
    rect(550, 150, 400, 150);
    
    push();
    stroke('#076678');
    rect(900, 75, 75, 250);
    rect(150, 75, 75, 250);
    pop();
   
    push();
    fill('#ebdbb2');
    //Main Street
    rect(450, 300, 100, 100);
    rect(0, 375, 1000, 25);
    pop();

    fill('#282828');
    rect(475, 300, 50, 100);

    //All of our arcs
    push();
    fill('#9d0006');
    arc(450, 150, 60, 60, HALF_PI, PI);
    arc(550, 150, 60, 60, 0, HALF_PI);
    stroke('#d5c4a1');
    arc(937, 75, 75, 75, PI, 0);
    pop();

    //Triangle roof
    push();
    stroke('#d5c4a1');
    triangle(150, 75, 187.5, 35, 225, 75);
    pop();

    //Water
    fill('#076678');
    rect(0, 400, 1000, 100);

    generateLights();

}

//Master state change function
function changeColor(state) {
    push();

    //If state < 0 nighttime
    if (state < 0) {

        //reDraw background for night, generate stars,
        //and set fill for moon
        background('#282828');
        generateNight(Math.random() * 50);
        fill('#928374');
    } else {
        
        //Day time background
        background('#7ec0ee');
    }

    //Master switch statement for moon / sun
    //Placement as well as color of sun
    // < 0 is a nighttime state
    switch(state) {
        case -1:
            ellipse(125, 100, 100); 
            break;

        case -2:
            ellipse(375, 50, 100);
            break;

        case -3:
            ellipse(625, 50, 100);
            break;

        case -4:
            ellipse(875, 100, 100);
            break;

        //For daytime ellipses use ternery '?'
        //Operator to check rise then eval our x
        //Since cases of 0, 1, 2, 3 will be hit
        //Twice in a single day cycle
        case 0:
            fill('#d65d0e');
            ellipse(rise ? 50 : 850, 100, 100);
            break;

        case 1:
            fill('#fe8019');
            ellipse(rise ? 150 : 750, 75, 100);
            break;

        case 2:
            fill('#d79921');
            ellipse(rise ? 250 : 650, 50, 100);
            break;

        case 3:
            fill('#fabd2f');
            ellipse(rise ? 350 : 550, 25, 100);
            break;

        //Only Hit once, peak sun, no need to check rise
        case 4:
            fill(255, 255, 0);
            ellipse(450, 0, 100); 
            break;
    }
    pop();
}

//Take in a number of stars and randomly place them around the canvas
function generateNight(numStars) {
    push();
    fill('#fabd2f');
    for (var i = 0; i < numStars; i++) {
        star(Math.random() * 1000, Math.random() * 500, 5, 15, 5);
    }
    pop();

}

//Turn on lights in windows during night
//This function is grossly hardcoded
function generateLights() {
    push();
    (state < 0 || setLights) && !initial ? fill('#fabd2f') : fill('#83a598');
    stroke('#282828');
    rect(175, 95, 25, 35);
    rect(175, 145, 25, 35);
    rect(175, 195, 25, 35);
    rect(925, 95, 25, 35);
    rect(925, 145, 25, 35);
    rect(925, 195, 25, 35);
    rect(85, 170, 25, 35);
    rect(85, 220, 25, 35);
    rect(250, 170, 25, 35);
    rect(250, 220, 25, 35);
    rect(300, 170, 25, 35);
    rect(300, 220, 25, 35);
    rect(350, 170, 25, 35);
    rect(350, 220, 25, 35);
    rect(400, 170, 25, 35);
    rect(400, 220, 25, 35);
    rect(600, 170, 25, 35);
    rect(600, 220, 25, 35);
    rect(650, 170, 25, 35);
    rect(650, 220, 25, 35);
    rect(700, 170, 25, 35);
    rect(700, 220, 25, 35);
    rect(750, 170, 25, 35);
    rect(750, 220, 25, 35);
    rect(800, 170, 25, 35);
    rect(800, 220, 25, 35);
    rect(850, 170, 25, 35);
    rect(850, 220, 25, 35);

    //I was going to fix how grossly hardcoded this is
    //but the logic gets a bit odd and I would rather focus
    //on making more things. I plan on revisiting this at some point
    
    /*
    var x = 250;
    for (var i = 0; i < 22; i++) {
        if (i == 7 || i == 21) {
            rect(x, 220, 25, 35);
        } else{
            i % 2 == 0 ? rect(x, 170, 25, 35)
                       : rect(x += 50, 220, 25, 35);
        }
        if (i == 7) x = 550;
    }*/
    pop();
}

//Code Sample from P5 JS reference
function star(x, y, radius1, radius2, npoints) {
  var angle = TWO_PI / npoints;
  var halfAngle = angle/2.0;
  beginShape();
  for (var a = 0; a < TWO_PI; a += angle) {
    var sx = x + cos(a) * radius2;
    var sy = y + sin(a) * radius2;
    vertex(sx, sy);
    sx = x + cos(a+halfAngle) * radius1;
    sy = y + sin(a+halfAngle) * radius1;
    vertex(sx, sy);
  }
  endShape(CLOSE);
}
