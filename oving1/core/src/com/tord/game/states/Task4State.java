package com.tord.game.states;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.tord.game.Oving1;
import com.tord.game.sprits.Ball;
import com.tord.game.sprits.Button;
import com.tord.game.sprits.Paddle;

public class Task4State extends State {
    private Texture background;
    private Button menuButton;
    private Paddle player1;
    private Paddle player2;
    private Ball ball;
    private BitmapFont score = new BitmapFont();
    private BitmapFont victoryBitmap = new BitmapFont();
    private String victoryText = "";
    private int pointsP1 = 0;
    private int pointsP2 = 0;

    public Task4State(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background.jpg");
        player1 = new Paddle(50, Oving1.HEIGHT/2 - 75, 30, 150, 8, Input.Keys.W, Input.Keys.S);
        player2 = new Paddle(Oving1.WIDTH - 80, Oving1.HEIGHT/2 - 75, 30, 150, 8, Input.Keys.O, Input.Keys.L);
        menuButton = new Button(
                gsm,
                0,
                cam,
                (Oving1.WIDTH / 8 - (75/2)),
                (Oving1.HEIGHT - 80)
        );
        ball = new Ball(Oving1.WIDTH/2 - 15, Oving1.HEIGHT/2 - 15, 30, 7);
    }


    @Override
    protected void handleInput() {
    }

    @Override
    public void update(float dt) {
        handleInput();

        boolean collision = false;

        if(Intersector.overlaps(ball.getBounds(), player1.getBounds())||
           Intersector.overlaps(ball.getBounds(), player2.getBounds())){
            collision = true;
        }

        player1.update(dt);
        player2.update(dt);
        ball.update(dt, collision);
        menuButton.update(dt);

        //gives out points if the ball exits screen
        if(ball.getPosition().x < 0){
            pointsP1++;
            ball.setPosition(new Vector3(Oving1.WIDTH/2 - 15, Oving1.HEIGHT/2 - 15, 0));
        }

        if(ball.getPosition().x > Oving1.WIDTH + ball.getDiameter()){
            pointsP2++;
            ball.setPosition(new Vector3(Oving1.WIDTH/2 - 15, Oving1.HEIGHT/2 - 15, 0));
        }

        if(pointsP2 >= 21){
            ball.setSpeed(0);
            victoryText = "Player 2 Won!";
        } else if (pointsP1 >= 21){
            ball.setSpeed(0);
            victoryText = "Player 1 Won!";
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        //sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, Oving1.WIDTH, Oving1.HEIGHT);
        sb.draw(ball.getTexture(), ball.getPosition().x, ball.getPosition().y);
        sb.draw(player1.getTexture(), player1.getPosition().x, player1.getPosition().y);
        sb.draw(player2.getTexture(), player2.getPosition().x, player2.getPosition().y);
        //make bigger and Center
        score.draw(sb, pointsP1 + " : " + pointsP2, Oving1.WIDTH/2, Oving1.HEIGHT - 20);
        victoryBitmap.draw(sb, victoryText, Oving1.WIDTH/2 - 40, Oving1.HEIGHT/2);
        sb.draw(menuButton.getTexture(), menuButton.getPosition().x, menuButton.getPosition().y);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
