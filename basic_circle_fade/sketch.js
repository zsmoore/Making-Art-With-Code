background_r = 255;
background_g = 0;
background_b = 140;
r_rising = false;
g_rising = false;
b_rising = false;
r = 0
g = 1
b = 2
y = screen.height;
x = screen.width;
general_num = 100;
max_color = 255;
min_color = 0;
fade_value = 1;

function setup() {

    createCanvas(x, y);
    background(background_r,
        background_g,
        background_b);
}

function draw() {
    //Fade in and out background
    updateAllColorsAndSetBackground();

    //Randomly Drawn Circles
    for (i = 0; i < 50; i++) {
        fill(((Math.random() * background_r) * background_r) / 2,
             ((Math.random() * background_g) * background_g) / 2,
             ((Math.random() * background_b) * background_b) / 2);
        ellipse(Math.random() * x,
            Math.random() * y,
            general_num,
            general_num); 
    }
    
}

function updateBackgroundColor(color) {

    //Switch on a color take in color, determine
    //if we should darken or lighten
    switch (color) {
        case r:
            r_rising ? background_r >= max_color
                           ? r_rising = false 
                           : background_r += fade_value
                     : background_r <= min_color
                           ? r_rising = true
                           : background_r -= fade_value;
            break;
        case g:
            g_rising ? background_g >= max_color
                           ? g_rising = false 
                           : background_g += fade_value 
                     : background_g <= min_color
                           ? g_rising = true
                           : background_g -= fade_value;
            break;
        case b:
            b_rising ? background_b >= max_color 
                           ? b_rising = false 
                           : background_b += fade_value
                     : background_b <= min_color
                           ? b_rising = true
                           : background_b -= fade_value; 
            break;
    }
}

function updateAllColorsAndSetBackground() {
    //Call All update Background Colors and reset background
    updateBackgroundColor(r);
    updateBackgroundColor(g);
    updateBackgroundColor(b);
    background(background_r,
        background_g,
        background_b);
}
