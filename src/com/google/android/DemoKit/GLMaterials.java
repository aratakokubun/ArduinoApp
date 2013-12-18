package com.google.android.DemoKit;

public class GLMaterials
{
	public static final int EMERALD = 0;
	public static final int JADE = 1;
	public static final int OBSIDIAN = 2;
	public static final int PEARL = 3;
	public static final int RUBY = 4;
	public static final int TURQUOISE = 5;
	public static final int BRASS = 6;
	public static final int BRONZE = 7;
	public static final int CHROME = 8;
	public static final int COPPER = 9;
	public static final int GOLD = 10;
	public static final int SILVER = 11;
	public static final int PLASTIC_BLACK = 12;
	public static final int PLASTIC_CYAN = 13;
	public static final int PLASTIC_GREEN = 14;
	public static final int PLASTIC_RED  =15;
	public static final int PLASTIC_WHITE = 16;
	public static final int PLASTIC_YELLOW = 17;
	public static final int RUBBER_BLACK = 18;
	public static final int RUBBER_CYAN = 19;
	public static final int RUBBER_GREEN = 20;
	public static final int RUBBER_RED  =21;
	public static final int RUBBER_WHITE = 22;
	public static final int RUBBER_YELLOW = 23;
	public static final int CHESS = 24;
	
	/* -------------------------------------------------- */
	//äeçﬁéøÇ≈ÇÃíl
	private static float emerald_ambient[]  = {0.0215f,  0.1745f,   0.0215f,  1.0f};
	private static float emerald_diffuse[]  = {0.07568f, 0.61424f,  0.07568f, 1.0f};
	private static float emerald_specular[] = {0.633f,   0.727811f, 0.633f,   1.0f};
	private static float emerald_shininess[]= {76.8f};

	private static float jade_ambient[]  = {0.135f,     0.2225f,   0.1575f,   1.0f};
	private static float jade_diffuse[]  = {0.54f,      0.89f,     0.63f,     1.0f};
	private static float jade_specular[] = {0.316228f,  0.316228f, 0.316228f, 1.0f};
	private static float jade_shininess[]= {12.8f};

	private static float obsidian_ambient[]  = {0.05375f, 0.05f,    0.06625f, 1.0f};
	private static float obsidian_diffuse[]  = {0.18275f, 0.17f,    0.22525f, 1.0f};
	private static float obsidian_specular[] = {0.332741f,0.328634f,0.346435f,1.0f};
	private static float obsidian_shininess[]= {38.4f};

	private static float pearl_ambient[]  = {0.25f,     0.20725f,  0.20725f,  1.0f};
	private static float pearl_diffuse[]  = {1f,        0.829f,    0.829f,    1.0f};
	private static float pearl_specular[] = {0.296648f, 0.296648f, 0.296648f, 1.0f};
	private static float pearl_shininess[]= {10.24f};

	private static float ruby_ambient[]  = {0.1745f,   0.01175f,  0.01175f,   1.0f};
	private static float ruby_diffuse[]  = {0.61424f,  0.04136f,  0.04136f,   1.0f};
	private static float ruby_specular[] = {0.727811f, 0.626959f, 0.626959f,  1.0f};
	private static float ruby_shininess[]= {76.8f};

	private static float turquoise_ambient[]  = {0.1f,     0.18725f, 0.1745f,  1.0f};
	private static float turquoise_diffuse[]  = {0.396f,   0.74151f, 0.69102f, 1.0f};
	private static float turquoise_specular[] = {0.297254f,0.30829f, 0.306678f,1.0f};
	private static float turquoise_shininess[]= {12.8f};

	private static float brass_ambient[]  = {0.329412f,  0.223529f, 0.027451f, 1.0f};
	private static float brass_diffuse[]  = {0.780392f,  0.568627f, 0.113725f, 1.0f};
	private static float brass_specular[] = {0.992157f,  0.941176f, 0.807843f, 1.0f};
	private static float brass_shininess[]= {27.89743616f};

	private static float bronze_ambient[]  = {0.2125f,   0.1275f,   0.054f,   1.0f};
	private static float bronze_diffuse[]  = {0.714f,    0.4284f,   0.18144f, 1.0f};
	private static float bronze_specular[] = {0.393548f, 0.271906f, 0.166721f,1.0f};
	private static float bronze_shininess[]= {25.6f};

	private static float chrome_ambient[]  = {0.25f,    0.25f,     0.25f,     1.0f};
	private static float chrome_diffuse[]  = {0.4f,     0.4f,      0.4f,      1.0f};
	private static float chrome_specular[] = {0.774597f,0.774597f, 0.774597f, 1.0f};
	private static float chrome_shininess[]= {76.8f};

	private static float copper_ambient[]  = {0.19125f,  0.0735f,   0.0225f,  1.0f};
	private static float copper_diffuse[]  = {0.7038f,   0.27048f,  0.0828f,  1.0f};
	private static float copper_specular[] = {0.256777f, 0.137622f, 0.086014f,1.0f};
	private static float copper_shininess[]= {12.8f};

    private static float gold_ambient[]  = {0.24725f,  0.1995f,   0.0745f,    1.0f};
    private static float gold_diffuse[]  = {0.75164f,  0.60648f,  0.22648f,   1.0f};
    private static float gold_specular[] = {0.628281f, 0.555802f, 0.366065f,  1.0f};
    private static float gold_shininess[]= {51.2f};

    private static float silver_ambient[]  = {0.19225f,  0.19225f,  0.19225f, 1.0f};
    private static float silver_diffuse[]  = {0.50754f,  0.50754f,  0.50754f, 1.0f};
    private static float silver_specular[] = {0.508273f, 0.508273f, 0.508273f,1.0f};
    private static float silver_shininess[]= {51.2f};

	private static float black_plastic_ambient[]  = {0.0f,    0.0f,    0.0f,  1.0f};
	private static float black_plastic_diffuse[]  = {0.01f,   0.01f,   0.01f, 1.0f};
	private static float black_plastic_specular[] = {0.50f,   0.50f,   0.50f, 1.0f};
	private static float black_plastic_shininess[]= {32};

	private static float cyan_plastic_ambient[]  ={0.0f,   0.1f,    0.06f,    1.0f};
	private static float cyan_plastic_diffuse[] ={0.0f,       0.50980392f,0.50980392f,1.0f};
	private static float cyan_plastic_specular[]={0.50196078f,0.50196078f,0.50196078f,1.0f};
	private static float cyan_plastic_shininess[]={32f};

	private static float green_plastic_ambient[]  = {0.0f,     0.0f,   0.0f,  1.0f};
	private static float green_plastic_diffuse[]  = {0.1f,     0.35f,  0.1f,  1.0f};
	private static float green_plastic_specular[] = {0.45f,    0.55f,  0.45f, 1.0f};
	private static float green_plastic_shininess[]= {32f};

	private static float red_plastic_ambient[]  = {0.0f,     0.0f,     0.0f,  1.0f};
	private static float red_plastic_diffuse[]  = {0.5f,     0.0f,     0.0f,  1.0f};
	private static float red_plastic_specular[] = {0.7f,     0.6f,     0.6f,  1.0f};
	private static float red_plastic_shininess[]= {32f};

	private static float white_plastic_ambient[]  = {0.0f,   0.0f,     0.0f,  1.0f};
	private static float white_plastic_diffuse[]  = {0.55f,  0.55f,    0.55f, 1.0f};
	private static float white_plastic_specular[] = {0.70f,  0.70f,    0.70f, 1.0f};
	private static float white_plastic_shininess[]= {32};

	private static float yellow_plastic_ambient[]  = {0.0f,  0.0f,     0.0f,  1.0f};
	private static float yellow_plastic_diffuse[]  = {0.5f,  0.5f,     0.0f,  1.0f};
	private static float yellow_plastic_specular[] = {0.60f, 0.60f,    0.50f, 1.0f};
	private static float yellow_plastic_shininess[]= {32f};

	private static float black_rubber_ambient[]  = {0.02f,   0.02f,    0.02f, 1.0f};
	private static float black_rubber_diffuse[]  = {0.01f,   0.01f,    0.01f, 1.0f};
	private static float black_rubber_specular[] = {0.4f,    0.4f,     0.4f,  1.0f};
	private static float black_rubber_shininess[]= {10.0f};

	private static float cyan_rubber_ambient[]  = {0.0f,     0.05f,    0.05f, 1.0f};
	private static float cyan_rubber_diffuse[]  = {0.4f,     0.5f,     0.5f,  1.0f};
	private static float cyan_rubber_specular[] = {0.04f,    0.7f,     0.7f,  1.0f};
	private static float cyan_rubber_shininess[]= {10.0f};

	private static float green_rubber_ambient[]  = {0.0f,    0.05f,    0.0f,  1.0f};
	private static float green_rubber_diffuse[]  = {0.4f,    0.5f,     0.4f,  1.0f};
	private static float green_rubber_specular[] = {0.04f,   0.7f,     0.04f, 1.0f};
	private static float green_rubber_shininess[]= {10.0f};

	private static float red_rubber_ambient[]  = {0.05f,     0.0f,     0.0f,  1.0f};
	private static float red_rubber_diffuse[]  = {0.5f,      0.4f,     0.4f,  1.0f};
	private static float red_rubber_specular[] = {0.7f,      0.04f,    0.04f, 1.0f};
	private static float red_rubber_shininess[]= {10.0f};

	private static float white_rubber_ambient[]  = {0.05f,   0.05f,    0.05f, 1.0f};
	private static float white_rubber_diffuse[]  = {0.5f,    0.5f,     0.5f,  1.0f};
	private static float white_rubber_specular[] = {0.7f,    0.7f,     0.7f,  1.0f};
	private static float white_rubber_shininess[]= {10.0f};

	private static float yellow_rubber_ambient[]  = {0.05f,  0.05f,    0.0f,  1.0f};
	private static float yellow_rubber_diffuse[]  = {0.5f,   0.5f,     0.4f,  1.0f};
	private static float yellow_rubber_specular[] = {0.7f,   0.7f,     0.04f, 1.0f};
	private static float yellow_rubber_shininess[]= {10.0f};

	private static float chess_ambient[] = {0.01f, 0.01f, 0.01f, 0.1f};
	private static float chess_diffuse[] = { 0.5f, 0.5f, 0.5f, 1.0f };
	private static float chess_specular[] = { 0.8f, 0.8f, 0.8f, 1.0f };
	private static float chess_shininess[] = {100f};

	/* -------------------------------------------------- */
	private static float AMBIENT[][] = {
		emerald_ambient,
		jade_ambient,
		obsidian_ambient,
		pearl_ambient,
		ruby_ambient,
		turquoise_ambient,
		brass_ambient,
		bronze_ambient,
		chrome_ambient,
		copper_ambient,
		gold_ambient,
		silver_ambient,
		black_plastic_ambient,
		cyan_plastic_ambient,
		green_plastic_ambient,
		red_plastic_ambient,
		white_plastic_ambient,
		yellow_plastic_ambient,
		black_rubber_ambient,
		cyan_rubber_ambient,
		green_rubber_ambient,
		red_rubber_ambient,
		white_rubber_ambient,
		yellow_rubber_ambient,
		chess_ambient,
	};

	private static float DIFFUSE[][] = {
		emerald_diffuse,
		jade_diffuse,
		obsidian_diffuse,
		pearl_diffuse,
		ruby_diffuse,
		turquoise_diffuse,
		brass_diffuse,
		bronze_diffuse,
		chrome_diffuse,
		copper_diffuse,
		gold_diffuse,
		silver_diffuse,
		black_plastic_diffuse,
		cyan_plastic_diffuse,
		green_plastic_diffuse,
		red_plastic_diffuse,
		white_plastic_diffuse,
		yellow_plastic_diffuse,
		black_rubber_diffuse,
		cyan_rubber_diffuse,
		green_rubber_diffuse,
		red_rubber_diffuse,
		white_rubber_diffuse,
		yellow_rubber_diffuse,
		chess_diffuse,
	};

	private static float SPECULAR[][] = {
		emerald_specular,
		jade_specular,
		obsidian_specular,
		pearl_specular,
		ruby_specular,
		turquoise_specular,
		brass_specular,
		bronze_specular,
		chrome_specular,
		copper_specular,
		gold_specular,
		silver_specular,
		black_plastic_specular,
		cyan_plastic_specular,
		green_plastic_specular,
		red_plastic_specular,
		white_plastic_specular,
		yellow_plastic_specular,
		black_rubber_specular,
		cyan_rubber_specular,
		green_rubber_specular,
		red_rubber_specular,
		white_rubber_specular,
		yellow_rubber_specular,
		chess_specular,
	};
	
	private static float SHININESS[][] = {
		emerald_shininess,
		jade_shininess,
		obsidian_shininess,
		pearl_shininess,
		ruby_shininess,
		turquoise_shininess,
		brass_shininess,
		bronze_shininess,
		chrome_shininess,
		copper_shininess,
		gold_shininess,
		silver_shininess,
		black_plastic_shininess,
		cyan_plastic_shininess,
		green_plastic_shininess,
		red_plastic_shininess,
		white_plastic_shininess,
		yellow_plastic_shininess,
		black_rubber_shininess,
		cyan_rubber_shininess,
		green_rubber_shininess,
		red_rubber_shininess,
		white_rubber_shininess,
		yellow_rubber_shininess,
		chess_shininess,
	};

	/* -------------------------------------------------- */
	private static int material = CHESS;
	
	/* -------------------------------------------------- */
	/**
	 * çﬁéøÇÃéwíË
	 */
	public static void setMaterial(int m)
	{
		material = m;
	}
	
	public static float[] getAmbient()
	{
		return AMBIENT[material];
	}
	public static float[] getAmbient(int m)
	{
		return AMBIENT[m];
	}
	
	public static float[] getDiffuse()
	{
		return DIFFUSE[material];
	}
	public static float[] getDiffuse(int m)
	{
		return DIFFUSE[m];
	}
	
	public static float[] getSpecular()
	{
		return SPECULAR[material];
	}
	public static float[] getSpecular(int m)
	{
		return SPECULAR[m];
	}
	
	public static float[] getShininess()
	{
		return SHININESS[material];
	}
	public static float[] getShininess(int m)
	{
		return SHININESS[m];
	}
}