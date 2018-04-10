# Metenorage
Game engine developed for class [*Game Engines Principles* **(8INF871)**](http://cours.uqac.ca/8INF871) at [UQAC](https://uqac.ca) (QC, CA). We implemented this engine in Java with the framework [LWJGL](https://www.lwjgl.org/) (Lightweight Java Game Library).

#### - Screenshots in-game of "FindYourWay" (See the [mini-game section](#minigame)) :

<img  width="430px" height="400px" src="https://github.com/Graygzou/Metenorage/blob/master/Images/image1.png"> <img width="430px" height="400px" src="https://github.com/Graygzou/Metenorage/blob/master/Images/image2.png">

#### - Engine Editor :
<img height="400px" src="https://github.com/Graygzou/Metenorage/blob/master/Images/editor.png">

## Libraries used
This engine is based on a Entity-Component-System (ECS) architecture. We used the following libraries :
* User Interfaces : [GLFW](http://www.glfw.org/)
* Rendering : [OpenGL](https://www.opengl.org/)
* Audio : [OpenAL](https://www.openal.org/)
* Physics : [JBullet](http://jbullet.advel.cz/)

## <a name="minigame"></a>Mini-game : "FindYourWay"
To test our engine, we developed a mini-game with it. You play as a box that has to jump over the void until 
you reached the final platform. Every time you fall, you lose a life. You can regain one by walking on a life item.

## Contributors
Please read the [contribution contract](CONTRIBUTING.md).

| Name          | Worked on         |
| ------------- |---------------|
| [Noémy Artigouha](https://github.com/Nono2602)    | Sounds system, Level design, Game programming |
| [Matthieu Le Boucher](https://github.com/Meight)  | Rendering system, Lighting system, Gameloop, Physics system |
| [Grégoire Boiron](https://github.com/Graygzou)    | Rendering system, Scripting system, Game editor |
| [Florian Vidal](https://github.com/FlorianVidal66)| Message queuing |
