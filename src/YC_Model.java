package net.minecraft.src;

import java.io.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;

public class YC_Model {
	
	List<Integer> vertexesi = new ArrayList<Integer>();//=================================
	List<Integer> texturesi = new ArrayList<Integer>();//=============INDEXES=============
	List<Integer> normalsi = new ArrayList<Integer>();// =================================
	List<Float> vertexes = new ArrayList<Float>();
	List<Float> textures = new ArrayList<Float>();
	List<Float> normals = new ArrayList<Float>();
	int ListIndex = 5;
	
	////////////////////////////////////////VBO////////////////////////////////////////
	int VBOID = 0;
	boolean VBOCreated = false;
	
	private YC_Model(){}
	
	/*
	 * Must have GL.END b4 it
	 */
	public void Draw(float x, float y, float z)
	{
		//GL11.glDisable(GL11.GL_CULL_FACE);
		if (YC_Options.ForceNativeRender)
		{
			if (YC_Options.EnableOptifineCompability)
			{
				DrawTesselaratorOptifine(x, y, z);
			}
			else
			{
				double x1 = Tessellator.instance.xOffset,y1 = Tessellator.instance.yOffset,z1=Tessellator.instance.zOffset;
				Tessellator.instance.setTranslation(x,y,z);
				DrawTesselarator();
				Tessellator.instance.setTranslation(x1, y1, z1);
			}
			return;
		}
		if (UseColor)
		{
			GL11.glColor3f(r, g, b);
			UseColor = false;
		}
		if (YC_Options.EnableOptifineCompability)
		{
			GL11.glTranslated(x+Tessellator.instance.xOffset, y+Tessellator.instance.yOffset, z+Tessellator.instance.zOffset);
		}
		else
		{
			GL11.glTranslatef(x, y, z);
		}
		if (!YC_Options.UseVBO)
		{
			GL11.glCallList(ListIndex);
		}
		else
		{
			DrawVBO();
		}
		if (YC_Options.EnableOptifineCompability)
		{
			GL11.glTranslated(-(x+Tessellator.instance.xOffset), -(y+Tessellator.instance.yOffset), -(z+Tessellator.instance.zOffset));
		}
		else
		{
			GL11.glTranslatef(-x, -y, -z);
		}
		//GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	/*
	 * Must have GL.END b4 it
	 */
	public void DrawWOTranslation()
	{
		//GL11.glDisable(GL11.GL_CULL_FACE);
		if (YC_Options.ForceNativeRender)
		{
			//if (YC_Options.EnableOptifineCompability)
			//{
			//	DrawTesselarator();
			//}
			//else
			//{
				DrawTesselarator();
			//}
			return;
		}
		if (UseColor)
		{
			GL11.glColor3f(r, g, b);
			UseColor = false;
		}
		if (!YC_Options.UseVBO)
		{
			GL11.glCallList(ListIndex);
		}
		else
		{
			DrawVBO();
		}
		//GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public void DrawWOTranslationAndWOBrightnessReset()
	{
		if (YC_Options.ForceNativeRender)
		{
			DrawTesselaratorWOBrightnessReset();
			return;
		}
		if (UseColor)
		{
			GL11.glColor3f(r, g, b);
			UseColor = false;
		}
		if (!YC_Options.UseVBO)
		{
			GL11.glCallList(ListIndex);
		}
		else
		{
			DrawVBO();
		}
	}
	
	float r,g,b;
	boolean UseColor = false;
	public void Draw(float r1, float g1, float b1, float x, float y, float z)
	{
		UseColor = true;
		r=r1;
		g=g1;
		b=b1;
		Draw(x,y,z);
	}
	
	public void DrawWOTranslation(float r1, float g1, float b1)
	{
		UseColor = true;
		r=r1;
		g=g1;
		b=b1;
		DrawWOTranslation();
	}
	
	public void DrawCwCcw(int x, float y, int z)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (YC_Options.ForceNativeRender)
		{
			if (YC_Options.EnableOptifineCompability)
			{
				DrawTesselaratorOptifine(x, y, z);
			}
			else
			{
				//GL11.glTranslatef(x, y, z);
				double x1 = Tessellator.instance.xOffset,y1 = Tessellator.instance.yOffset,z1=Tessellator.instance.zOffset;
				Tessellator.instance.setTranslation(x,y,z);
				DrawTesselarator();
				Tessellator.instance.setTranslation(x1, y1, z1);
				//GL11.glTranslatef(-x, -y, -z);
			}
			GL11.glEnable(GL11.GL_CULL_FACE);
			return;
		}
		if (YC_Options.EnableOptifineCompability)
		{
			GL11.glTranslated(x+Tessellator.instance.xOffset, y+Tessellator.instance.yOffset, z+Tessellator.instance.zOffset);
		}
		else
		{
			GL11.glTranslatef(x, y, z);
		}
		if (!YC_Options.UseVBO)
		{
			GL11.glCallList(ListIndex);
		}
		else
		{
			DrawVBO();
		}
		if (YC_Options.EnableOptifineCompability)
		{
			GL11.glTranslated(-(x+Tessellator.instance.xOffset), -(y+Tessellator.instance.yOffset), -(z+Tessellator.instance.zOffset));
		}
		else
		{
			GL11.glTranslatef(-x, -y, -z);
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
	}

	public void DrawCwCcwWOTranslation(int x, int y, int z)
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
		if (YC_Options.ForceNativeRender)
		{
			DrawTesselarator();
			GL11.glEnable(GL11.GL_CULL_FACE);
			return;
		}
		if (!YC_Options.UseVBO)
		{
			GL11.glCallList(ListIndex);
		}
		else
		{
			DrawVBO();
		}
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	public void DrawTesselarator()
	{
		Tessellator t = Tessellator.instance;
		t.startDrawing(GL11.GL_TRIANGLES);
		if (UseColor)
		{
			t.setColorOpaque_F(r, g, b);
			r=0;
			g=0;
			b=0;
			UseColor = false;
		}
        t.setBrightness(0x00F0);
		for(int i = 0; i<vertexesi.size()/3; i++)
		{
			BindVertexTesselarator(t,i*3);
			BindVertexTesselarator(t,i*3+1);
			BindVertexTesselarator(t,i*3+2);
		}
		t.draw();
	}
	
	public void DrawTesselaratorWOBrightnessReset()
	{
		Tessellator t = Tessellator.instance;
		t.startDrawing(GL11.GL_TRIANGLES);
		if (UseColor)
		{
			t.setColorOpaque_F(r, g, b);
			r=0;
			g=0;
			b=0;
			UseColor = false;
		}
		for(int i = 0; i<vertexesi.size()/3; i++)
		{
			BindVertexTesselarator(t,i*3);
			BindVertexTesselarator(t,i*3+1);
			BindVertexTesselarator(t,i*3+2);
		}
		t.draw();
	}
	
	public void DrawTesselaratorOptifine(float xt, float yt, float zt)
	{
		Tessellator t = Tessellator.instance;
		t.startDrawing(GL11.GL_TRIANGLES);
		if (UseColor)
		{
			t.setColorOpaque_F(r, g, b);
			r=0;
			g=0;
			b=0;
			UseColor = false;
		}
        t.setBrightness(0x00F0);
		for(int i = 0; i<vertexesi.size()/3; i++)
		{
			BindVertexTesselaratorOptifine(t,i*3, xt, yt, zt);
			BindVertexTesselaratorOptifine(t,i*3+1, xt, yt, zt);
			BindVertexTesselaratorOptifine(t,i*3+2, xt, yt, zt);
		}
		t.draw();
	}
	
	public void BindVertexTesselarator(Tessellator tes, int i)
	{
		int tt=(texturesi.get(i)-1)*2;
		int t=(vertexesi.get(i)-1)*3;
		
		tes.addVertexWithUV(vertexes.get(t), 
				vertexes.get(t+1),
				vertexes.get(t+2), 
				(textures.get(tt)), 
				(1-textures.get(tt+1)));
	}
	
	public void BindVertexTesselaratorOptifine(Tessellator tes, int i, float xt, float yt, float zt)
	{
		int tt=(texturesi.get(i)-1)*2;
		int t=(vertexesi.get(i)-1)*3;
		
		tes.addVertexWithUV(vertexes.get(t)+xt, 
				vertexes.get(t+1)+yt,
				vertexes.get(t+2)+zt, 
				(textures.get(tt)), 
				(1-textures.get(tt+1)));
	}

	public void Draw(int texture, float x, float y, float z)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		Draw(x,y,z);
	}

	public void DrawCwCcw(int texture, int x, int y, int z)
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		DrawCwCcw(x,y,z);
	}
	
	private void BindVertex(int i)
	{
		int tt=(texturesi.get(i)-1)*2;
		GL11.glTexCoord2f((textures.get(tt)), 
				(1-textures.get(tt+1)));
		
		tt=(normalsi.get(i)-1)*3;
		GL11.glNormal3f(normals.get(tt), 
				normals.get(tt+1),
				normals.get(tt+2));
		
		int t=(vertexesi.get(i)-1)*3;
		GL11.glVertex3f(vertexes.get(t), 
				vertexes.get(t+1),
				vertexes.get(t+2));
	}
	
	public static YC_Model LoadFromFS(String name) throws IOException
	{
		YC_Model m = new YC_Model();
		FileInputStream fstream = new FileInputStream(name);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String s;
		float[] t = null;
		
		while(true)
		{
			s=br.readLine();
			if (s==null) break; //eof
			
			if (s.startsWith("v "))//vertex
			{
				t = VStrToArr(s);
				m.vertexes.add(t[0]);
				m.vertexes.add(t[1]);
				m.vertexes.add(t[2]);
				continue;
			}
			if (s.startsWith("vt"))//texture
			{
				t = TNStrToArr(s);
				m.textures.add(t[0]);
				m.textures.add(t[1]);
				//m.textures.add(t[2]);
				continue;
			}
			if (s.startsWith("vn"))//normal
			{
				t = TNStrToArr(s);
				m.normals.add(t[0]);
				m.normals.add(t[1]);
				m.normals.add(t[2]);
				continue;
			}
			
			
			if (s.startsWith("f"))//normal
			{
				m.ProcessFString(s);
				continue;
			}
		}
		
		in.close();
		
		m.GenerateVBO();
		return m;
	}
	
	public static YC_Model Load(String name) throws IOException
	{
		YC_Model m = new YC_Model();
		InputStream fstream = Minecraft.class.getResourceAsStream(name);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String s;
		float[] t = null;
		
		while(true)
		{
			s=br.readLine();
			if (s==null) break; //eof
			
			if (s.startsWith("v "))//vertex
			{
				t = VStrToArr(s);
				m.vertexes.add(t[0]);
				m.vertexes.add(t[1]);
				m.vertexes.add(t[2]);
				continue;
			}
			if (s.startsWith("vt"))//texture
			{
				t = TNStrToArr(s);
				m.textures.add(t[0]);
				m.textures.add(t[1]);
				//m.textures.add(t[2]);
				continue;
			}
			if (s.startsWith("vn"))//normal
			{
				t = TNStrToArr(s);
				m.normals.add(t[0]);
				m.normals.add(t[1]);
				m.normals.add(t[2]);
				continue;
			}
			
			
			if (s.startsWith("f"))//normal
			{
				m.ProcessFString(s);
				continue;
			}
		}
		
		in.close();
		
		m.GenerateVBO();
		return m;
	}
	
	private static float[] VStrToArr(String s)
	{
		String[] t = s.split(" ");
		return new float[]{Float.parseFloat(t[2]), Float.parseFloat(t[3]),
				Float.parseFloat(t[4])};
	}
	
	private static float[] TNStrToArr(String s)
	{
		String[] t = s.split(" ");
		return new float[]{Float.parseFloat(t[1]), Float.parseFloat(t[2]),
				Float.parseFloat(t[3])};
	}
	
	private void ProcessFString(String s)
	{
		String[] t = s.split(" ");
		int[] ta = null;
		ta = ProcessFSegment(t[1]);
		vertexesi.add(ta[0]);
		texturesi.add(ta[1]);
		normalsi.add(ta[2]);
		ta = ProcessFSegment(t[2]);
		vertexesi.add(ta[0]);
		texturesi.add(ta[1]);
		normalsi.add(ta[2]);
		ta = ProcessFSegment(t[3]);
		vertexesi.add(ta[0]);
		texturesi.add(ta[1]);
		normalsi.add(ta[2]);
	}
	
	private int[] ProcessFSegment(String s)
	{
		String[] t = s.split("/");
		return new int[]{Integer.parseInt(t[0]), Integer.parseInt(t[1]),
				Integer.parseInt(t[2])};
	}
	
	public void GenerateVBO()
	{
		if (YC_Options.ForceNativeRender)
			return;
		if (!YC_Options.UseVBO)
		{
			ListIndex = YC_Mod.GetNextListIndex();
			GL11.glNewList(ListIndex, GL11.GL_COMPILE);
			DrawVertexes();
			GL11.glEndList();
		}
		else
		{
			CreateVBO();
		}
	}
	
	public void CreateVBO()
	{
		  FloatBuffer vcBuffer = BufferUtils.createFloatBuffer(vertexesi.size()*3*2);

	      int t = 0;
	      for(int i = 0; i<vertexesi.size(); i++)
	      {
			t=(vertexesi.get(i)-1)*3;
		      vcBuffer.put(vertexes.get(t)).put(vertexes.get(t+1)).put(vertexes.get(t+2)); // v
	      }
	      for(int i = 0; i<vertexesi.size(); i++)
	      {
	  		t=(texturesi.get(i)-1)*2;
		      vcBuffer.put(textures.get(t)).put(1-textures.get(t+1)); // t
	      }

	      vcBuffer.flip();

	      VBOID = ARBVertexBufferObject.glGenBuffersARB();

	      GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	      GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);

	      ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, VBOID);
	      ARBVertexBufferObject.glBufferDataARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, vcBuffer, ARBVertexBufferObject.GL_DYNAMIC_DRAW_ARB);

	      ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

	      GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	      GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	}
	
	public void CleanVBO()
	{
	      ARBVertexBufferObject.glDeleteBuffersARB(VBOID);
	}
	
	void DrawVBO()
	   {
	      GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	      GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		  ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, VBOID);
	      
	      GL11.glVertexPointer(3, GL11.GL_FLOAT, 3<<2, 0<<2);
	      GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 2<<2, (3*vertexesi.size())<<2);

	      GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexesi.size());

	      ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);

	      GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
	      GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
	   }
	
	public void DrawVertexes()
	{
		GL11.glBegin(GL11.GL_TRIANGLES);
		int i = 0;
		for(i = 0; i<vertexesi.size()/3; i++)
		{
			//BindVertex(i*3+2);
			//BindVertex(i*3+1);
			//BindVertex(i*3);
			BindVertex(i*3);
			BindVertex(i*3+1);
			BindVertex(i*3+2);
		}
		GL11.glEnd();
	}
	
}
