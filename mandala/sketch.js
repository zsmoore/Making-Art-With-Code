var rectMap1, rectMap2, rectMap3, rectMap4;
var coord1, coord2, coord3, coord4;
var xPos, yPos;
var len = 100;
var lenH = len / 2;
var radius1 = 400;
var radius2 = 500;
var moving = false;
var state = 0;

var myDia = 100;
var myVal;

function setup() {
    createCanvas(1000, 1000);
    background('#fbf1c7');
    fill('#cc241d');
    stroke('#fbf1c7');
    rectMode(CENTER);

    //initial coords
    coord1 = width / 4;
    coord2 = height / 4;
    coord3 = coord1 * 3;
    coord4 = coord2 * 3;
}

function draw() {
    background('#fbf1c7');

    //Map example from class based on Y value
    push();
    fill('#79740e');
    myVal = mouseY;
    rect(width / 2, height / 2, myDia, myDia);
    myDia = map(myVal, 0, height, 100, 1000);
    pop();

    //Rotating star
    push();
    translate(500, 400);
    rotate(frameCount / 400);
    star(height/2, width/2, 400, 500, 30);
    pop();

    //Circles that move with mouse
    push();
    fill('#076678');
    ellipse(mouseX, 0 + mouseY, len, len);
    ellipse(mouseX, height - mouseY, len, len);
    ellipse(mouseX * 1.25, mouseY, len, len);
    ellipse(mouseX * 1.25, height - mouseY, len, len);
    ellipse(mouseX * .75, mouseY, len, len);
    ellipse(mouseX * .75, height - mouseY, len, len);
    ellipse(mouseX * 1.75, mouseY, len, len);
    ellipse(mouseX * 1.75, height - mouseY, len, len);
    ellipse(mouseX * .25, mouseY, len, len);
    ellipse(mouseX * .25, height - mouseY, len, len);
    pop();

    //Draw our 4 rectangles
    push();
    fill('#cc241d');
    rect(coord1, coord2, len, len);
    rect(coord3, coord2, len, len);
    rect(coord1, coord4, len, len);
    rect(coord3, coord4, len, len); 
    pop();

    //On drag draw circles
    push();
    fill('#8f3f71');
    triangle(xPos, yPos,
             xPos + xPos * 1.25,
             yPos + yPos * .75,
             xPos + xPos * .75,
             yPos + yPos * 1.25);
    triangle(xPos, height - yPos,
             xPos + xPos * 1.25,
             height - yPos - yPos * .75,
             xPos + xPos * .75,
             height - yPos - yPos * 1.25);
    triangle(xPos, yPos,
             xPos - xPos * 1.25,
             yPos + yPos * .75,
             xPos - xPos * .75,
             yPos + yPos * 1.25);
    triangle(xPos, height - yPos,
             xPos - xPos * 1.25,
             height - yPos - yPos * .75,
             xPos - xPos * .75,
             height - yPos - yPos * 1.25);
    pop();

    //Initial starting of moving
    if (inRects() && !moving) {
        updateCoords();
        moving = true;
    }
    //If triggered and not done moving continue
    if (moving) {
        updateCoords();
    }
}

//Detect if mouse is in one of our 4 rectangles
function inRects() {
    //Each if represents one rectangle    
    if (mouseX < coord1 + lenH 
        && mouseX > coord1 - lenH
        && mouseY < coord2 + lenH
        && mouseY > coord2 - lenH) {
        return true;
    } else if (mouseX < coord3 + lenH
              && mouseX > coord3 - lenH
              && mouseY < coord2 + lenH
              && mouseY > coord2 - lenH) {
        return true;
    } else if (mouseX < coord1 + lenH
              && mouseX > coord1 - lenH
              && mouseY < coord4 + lenH
              && mouseY > coord4 - lenH) {
        return true;
    } else if (mouseX < coord3 + lenH
              && mouseX > coord3 - lenH
              && mouseY < coord4 + lenH
              && mouseY > coord4 - lenH) {
        return true;
    } else {
        return false;
    }
}

//Incerements coords until we are at our finished positions
function updateCoords() {
    var bound3 = (width / 4) * 3;
    //If we are not at the edges then shift coords
    if (coord1 != bound3) {
        coord1 += 1;
        coord2 += 1;
        coord3 -= 1;
        coord4 -= 1;
    } else {
        //Once we are at the corners then reset
        moving = false;
        coord1 = width / 4;
        coord2 = height / 4;
        coord3 = coord1 * 3;
        coord4 = coord2 * 3;
    }
}

//Taken from P5JS examples page
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

function mouseClicked() {

    //If we click continue shifting states
    //until we hit the final state then reset
    state +=1;
    if (state == 5){
        state = 0;
    }

    switch (state) {
        case 0:
            fill('#b16286');
            break;
        case 1:
            fill('#d79921');
            break;
        case 2:
            fill('#b8bb26');
            break;
        case 3:
            fill('#076678');
            break;
        case 4:
            fill('#689d6a');
            break;
        default:
            fill('#cc241d');
            break;
    }
}

function mouseDragged() {
    xPos = mouseX;
    yPos = mouseY;
}
