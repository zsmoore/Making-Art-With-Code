let detailVal = 64;
let numBole = 512;

class Bole {
    constructor(off) {
        this.rot = 0;
        this.off = off;
    }

    display() {
        rotateY(radians(360 / numBole));
        
        // Handle setting rotate Y on same pane
        push();
        rotateZ(radians(this.rot + this.off));
        //torus(250, 1, detailVal, detailVal);
        
        // Handle setting circle on top of each other
        push();
        translate(250, 0, 0);
        sphere(20, detailVal, detailVal);
        pop();
        pop();

        this.rot += 1;
    }
}

let objs = [];
function setup() {
    createCanvas(innerWidth, innerHeight, WEBGL);
    fill('#ebdbb2');

    let off = 0;
    for(let i = 0; i < numBole; i++) {
        objs.push(new Bole(off));
        off += 360 / numBole;
    }
}

function draw() {
   background('#282828');

   for(let i = 0; i < objs.length; i++) {
       objs[i].display();
   }
}

