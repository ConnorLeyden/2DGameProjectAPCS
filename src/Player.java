import java.awt.*;
public class Player extends GameObject{
    public static Player instance;
    private static final double ZOOM_SPEED = 0.03;

    public static final Vector2 initialPos = new Vector2(0, -50);

    private boolean isNoClip = false;
    public TileType[] inventory = {Tiles.DIRT, Tiles.REDDIRT, Tiles.STONE, Tiles.BASALT};
    private int selectedInventorySlot = 0;
    public double zoom = 0.03;
    public double speed = 15;
    public double terminalVelocity = 30;
    public double jumpPower = 10;
    public Vector2 gravity = new Vector2(0, 16);
    public Vector2 size = new Vector2(0.8, 1.8);
    public Vector2 pos = new Vector2(0,-50);

    public Vector2 velocity = new Vector2(0, 0);

    private boolean isGrounded = false;

    private final Collider collider = new Collider() {
        @Override
        public Vector2 getCenter() {
            return pos;
        }

        @Override
        public void setCenter(Vector2 value) {
            pos = value;
        }

        @Override
        public Vector2 getSize() {
            return size;
        }

        @Override
        public void onBottomCollide() {
            isGrounded = true;
            if(velocity.y > 0) velocity = velocity.withY(0);
        }

        @Override
        public void onTopCollide() {
            if(velocity.y < 0) velocity = velocity.withY(0);
        }
    };

    public Player(){
        instance = this;
    }

    @Override
    public void render(Renderer r) {
        //r.setColor(new Color(((float) Math.sin(Main.getTime()) + 1f)/2f,0f,0f));
        r.drawRectWorldSpace(pos.sub(size.scale(0.5)), size);

        r.graphics().drawString("FPS: " + (1/Main.getUncappedDeltaTime()), 10, 10);
        r.graphics().drawString("NoClip: " + this.isNoClip, 10, 21);
        if (!KeyboardKeybind.anyKeyPressed) {
            printTutorial(r);
        }
        printCoordinates(r);
        printInventory(r);

    }

    public void printTutorial(Renderer r) {
        Font font = new Font("Arial", Font.BOLD, 18);
        r.setFont(font);

        r.graphics().drawString("Below are the features and controls for this game:", 270, 20);
        r.graphics().drawString("The A and D keys move the player to the left and right respectively", 300, 40);
        r.graphics().drawString("The space key allows the player to jump", 300, 60);
        r.graphics().drawString("Clicking the left mouse button breaks a block, while clicking the right mouse button places one", 300, 80);
        r.graphics().drawString("The R key resets the player to the original position", 300, 100);
        r.graphics().drawString("The T key teleports the player to the nearest unfilled location in the vicinity of the cursor's current location", 300, 120);
        r.graphics().drawString("The N key toggles \"No Clip\" mode, which makes the player not collide with anything, not experience gravity, ", 300, 140);
        r.graphics().drawString("and be able to move freely in all 4 directions", 300, 160);
        r.graphics().drawString("Press any keyboard button to escape this tutorial", 300, 180);
    }

    public void printInventory(Renderer r) {
        Font font = new Font("Arial", Font.PLAIN, 16);
        Font bigFont = new Font("Arial", Font.PLAIN, 20);
        Font boldFont = new Font("Arial", Font.BOLD, 16);
        r.setFont(bigFont);
        r.graphics().drawString("Inventory:", 0, 40);
        for (int i = 0; i < inventory.length; i++) {
            if (selectedInventorySlot == i) {
                r.setFont(boldFont);
            } else {
                r.setFont(font);
            }
            r.graphics().drawString(""+(i+1)+". " + inventory[i].blockType, 0, 55 + 15 * i);
        }
    }

    public void printCoordinates(Renderer r) {
        r.graphics().drawString("x: "+pos.x, 1100, 20);
        r.graphics().drawString("y: "+pos.y, 1100, 30);
    }
    @Override
    public void tick() {
        if(Input.moveLeft.isDown()) {
            pos = pos.add(new Vector2(-speed, 0.0).scale(Main.getDeltaTime()));
        }

        if(Input.moveRight.isDown()) {
            pos = pos.add(new Vector2(speed, 0.0).scale(Main.getDeltaTime()));
        }

        if (!isNoClip) {
            velocity = velocity.add(gravity.scale(Main.getDeltaTime()));
            velocity = velocity.withY(Math.min(velocity.y, terminalVelocity));
            pos = pos.add(velocity.scale(Main.getDeltaTime()));

            isGrounded = false;
            collider.processCollisionWithTerrain();
        } else {
            velocity = velocity.withY(Math.min(velocity.y, terminalVelocity));
            pos = pos.add(velocity.scale(Main.getDeltaTime()));
            isGrounded = false;
            if(Input.moveDown.isDown()) {
                pos = pos.add(new Vector2(0.0, speed).scale(Main.getDeltaTime()));
            }

            if(Input.moveUp.isDown()) {
                pos = pos.add(new Vector2(0.0, -speed).scale(Main.getDeltaTime()));
            }
        }

        if(Input.jump.isPressed() && isGrounded){
            velocity = velocity.withY(-jumpPower);
        }

        Vector2Int mouseTile = Renderer.screenToWorldPos(Input.getMousePosition().toVector()).floorToInt();

        if (Input.reset.isPressed()) {
            pos = initialPos;
        }

        if (Input.noClip.isPressed()) {
            isNoClip = !isNoClip;
        }

        if (Input.oneKey.isPressed()) {
            selectedInventorySlot = 0;
        }

        if (Input.twoKey.isPressed()) {
            selectedInventorySlot = 1;
        }

        if (Input.threeKey.isPressed()) {
            selectedInventorySlot = 2;
        }

        if (Input.teleport.isPressed()) {
            pos = Renderer.screenToWorldPos(Input.getMousePosition().toVector());
        }

        if(Input.use.isPressed()){
            World.setMainTile(mouseTile, null);
        }

        if(Input.place.isPressed()){
            World.setMainTile(mouseTile, inventory[selectedInventorySlot]);
        }

        if(Input.zoomIn.isDown()){
            zoom += ZOOM_SPEED * Main.getDeltaTime();
        }

        if(Input.zoomOut.isDown()){
            zoom -= ZOOM_SPEED * Main.getDeltaTime();
        }

        zoom = Math.max(zoom, 0.001);
    }
}
