package com.damagedearth.Gui.Components;

import com.damagedearth.DamagedEarth;
import com.damagedearth.Entities.Components.EntityNPC;
import com.damagedearth.GameElements.Quests.Components.BasicQuest;
import com.damagedearth.Utilities.MouseHelper;
import com.damagedearth.Worlds.BasicWorld;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.glRectd;

public class GuiNPC extends GuiScreen
{
    protected List<GuiNPCQuestDialogue> selectableList = new ArrayList<GuiNPCQuestDialogue>();
    private EntityNPC npc;

    /**
     * @param damagedEarth An instance of Damaged Earth
     * @param displayName  The visible name of the GUI
     * @param parentScreen The parent screen of the gui
     */
    public GuiNPC(DamagedEarth damagedEarth, String displayName, GuiScreen parentScreen, EntityNPC npc)
    {
        super(damagedEarth, displayName, parentScreen);
        this.npc = npc;

        for (BasicQuest quest : npc.getCurrentQuests())
        {
            GuiNPCQuestDialogue questDialogue = new GuiNPCQuestDialogue(quest.getQuestName(), "lalalala", false, this);
            questDialogue.setQuest(quest);
            this.selectableList.add(questDialogue);
        }
        this.npc.setGuiNPC(this);
    }

    @Override
    public void setUpGUI()
    {
        super.setUpGUI();
    }

    @Override
    public void handleInput()
    {
        if (MouseHelper.getMouseClick(0))
        {
            for (GuiNPCQuestDialogue selectableDialogue : this.selectableList)
            {
                if (selectableDialogue.isEnabled())
                {
                    if (MouseHelper.insideAreaW(selectableDialogue.getX(), selectableDialogue.getY(), selectableDialogue.getWidth(), selectableDialogue.getHeight()))
                    {
                        System.out.println("Button has been clicked: " + mouseAction(0, selectableDialogue));
                    }
                }
            }
            for (GuiButton button : this.buttonList)
            {
                if (button.isEnabled)
                {
                    if (MouseHelper.insideAreaW(button.x, button.y, button.width, button.height))
                    {
                        System.out.println("Button has been clicked: " + mouseAction(0, button));
                    }
                }
            }
        }
        else if (MouseHelper.getMouseClick(1))
        {
            for (GuiNPCQuestDialogue selectableDialogue : this.selectableList)
            {
                if (selectableDialogue.isEnabled())
                {
                    if (MouseHelper.insideAreaW(selectableDialogue.getX(), selectableDialogue.getY(), selectableDialogue.getWidth(), selectableDialogue.getHeight()))
                    {
                        System.out.println("Button has been clicked: " + mouseAction(1, selectableDialogue));
                    }
                }
            }
            for (GuiButton button : this.buttonList)
            {
                if (button.isEnabled)
                {
                    if (MouseHelper.insideAreaW(button.x, button.y, button.width, button.height))
                    {
                        System.out.println("Button has been clicked: " + mouseAction(0, button));
                    }
                }
            }
        }

        if (this.checkKey(Keyboard.KEY_ESCAPE))
        {
            this.damagedEarth.switchScreen(parentScreen);
        }
    }

    public int mouseAction(int key, GuiNPCQuestDialogue selectableDialogue)
    {
        if (key == 0)
        {
            if (selectableDialogue.isSelected())
            {
                selectableDialogue.unSelect();
            }
            else
            {
                selectableDialogue.select();
            }
        }
        return 0;
    }

    @Override
    public void updateScreen()
    {
        int yOffset = BasicWorld.VIEW_CORDS_Y + 194;
        this.drawGLScreen();

        //Puts selectables in correct places
        for (GuiNPCQuestDialogue selectable : this.selectableList)
        {
            selectable.setX(BasicWorld.VIEW_CORDS_X + 4);
            selectable.setWidth(this.damagedEarth.width / 2 - 6);
            selectable.setY(yOffset);
            selectable.setHeight(100);

            yOffset += 104;

            selectable.update();
        }

        for (GuiButton button : this.buttonList)
        {
            button.update();
        }
        this.handleInput();
    }

    @Override
    public void drawGLScreen()
    {
        //Make sure the rectangle is drawn in perspective with the scrolling cords
        glRectd(BasicWorld.VIEW_CORDS_X + 2, BasicWorld.VIEW_CORDS_Y + 92, BasicWorld.VIEW_CORDS_X + this.damagedEarth.width / 2, BasicWorld.VIEW_CORDS_Y + this.damagedEarth.height - 2);
    }

    public List<GuiNPCQuestDialogue> getSelectableList()
    {
        return selectableList;
    }

    public EntityNPC getNpc()
    {
        return npc;
    }

    public void setNpc(EntityNPC npc)
    {
        this.npc = npc;
    }
}
