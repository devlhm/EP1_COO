import java.awt.*;
import java.util.Random;
import java.lang.Math;

/**
	Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
	instancia um objeto deste tipo quando a execução é iniciada.
*/

public class Ball {

	private double cx;
	private double cy; 
	private double width;
	private double height;
	private Color color;
	private double speed;
	private double dirx;
	private double diry;


	/**
		Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola 
		(em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada 
		aleatóriamente pelo construtor.

		@param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
		@param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
		@param width largura do retangulo que representa a bola.
		@param height altura do retangulo que representa a bola.
		@param color cor da bola.
		@param speed velocidade da bola (em pixels por millisegundo).
	*/

	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;

		Random rand = new Random();

		//gera dois double entre -1 e 1
		double randX = rand.nextDouble() * 2 - 1;
		double randY = rand.nextDouble() * 2 - 1;

		//normalizando a direção
		double dirLength = Math.sqrt((randX*randX) + (randY*randY));
		dirx = randX/dirLength;
		diry = randY/dirLength;
		diry = -1;

		System.out.println(dirx);
		System.out.println(diry);
	}


	/**
		Método chamado sempre que a bola precisa ser (re)desenhada.
	*/

	public void draw(){
		
		GameLib.setColor(color);
		GameLib.fillRect(cx, cy, width, height);
	}

	/**
		Método chamado quando o estado (posição) da bola precisa ser atualizado.
		
		@param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	*/

	public void update(long delta){
		cx += dirx*speed*delta;
		cy += diry*speed*delta;
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com um jogador.
	
		@param playerId uma string cujo conteúdo identifica um dos jogadores.
	*/

	public void onPlayerCollision(String playerId){
		Random rand = new Random();
		
		dirx *= -1;
		diry = rand.nextDouble() * 2 - 1;
	}

	/**
		Método chamado quando detecta-se uma colisão da bola com uma parede.

		@param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	*/

	public void onWallCollision(String wallId){
		if(wallId == Pong.TOP || wallId == Pong.BOTTOM)
			diry *= -1;
		else
			dirx *= -1;
	}

	// Verifica se há inteseção entre dois intervalos

	private boolean intersectRange(double min0, double max0, double min1, double max1) {
		return max0 >= min1 && min0 <= max1;
	}

	/**
		Método que verifica se houve colisão da bola com uma parede.

		@param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/
	
	public boolean checkCollision(Wall wall){		
		double ballXRad = width/2;
		double ballYRad = height/2;
		double wallXRad = wall.getWidth()/2;
		double wallYRad = wall.getHeight()/2;

		boolean collisionX = intersectRange(cx-ballXRad, cx+ballXRad, wall.getCx()-wallXRad, wall.getCx()+wallXRad);
		boolean collisionY = intersectRange(cy-ballYRad, cy+ballYRad, wall.getCy()-wallYRad, wall.getCy()+wallYRad);

		return collisionX && collisionY;
	}

	/**
		Método que verifica se houve colisão da bola com um jogador.

		@param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
		@return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	*/	

	public boolean checkCollision(Player player){
		double ballXRad = width/2;
		double ballYRad = height/2;
		double playerXRad = player.getWidth()/2;
		double playerYRad = player.getHeight()/2;

		boolean collisionX = intersectRange(cx-ballXRad, cx+ballXRad, player.getCx()-playerXRad, player.getCx()+playerXRad);
		boolean collisionY = intersectRange(cy-ballYRad, cy+ballYRad, player.getCy()-playerYRad, player.getCy()+playerYRad);

		return collisionX && collisionY;
	}

	/**
		Método que devolve a coordenada x do centro do retângulo que representa a bola.
		@return o valor double da coordenada x.
	*/
	
	public double getCx(){

		return cx;
	}

	/**
		Método que devolve a coordenada y do centro do retângulo que representa a bola.
		@return o valor double da coordenada y.
	*/

	public double getCy(){

		return cy;
	}

	/**
		Método que devolve a velocidade da bola.
		@return o valor double da velocidade.

	*/

	public double getSpeed(){

		return speed;
	}

}
