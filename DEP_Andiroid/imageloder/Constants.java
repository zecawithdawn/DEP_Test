/*******************************************************************************
 * Copyright 2011-2013 Sergey Tarasevich
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.posin.myapplication.imageloder;

import com.example.posin.myapplication.R;

/**
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 */
public final class Constants {
	//이미지등록
	public static final String[] introPic = new String[] {
			"drawable://" + R.drawable.posinintro,
	};
	public static final String[] mainPic = new String[] {
			"drawable://" + R.drawable.main_examine_bt,
			"drawable://" + R.drawable.main_sound_bt,
			"drawable://" + R.drawable.main_emdr_bt,
	};
	public static final String[] soundPic = new String[] {
			"drawable://" + R.drawable.sound_bg,
			"drawable://" + R.drawable.sound_sea_bt,
			"drawable://" + R.drawable.sound_rain_bt,
			"drawable://" + R.drawable.sound_shine_bt,
			"drawable://" + R.drawable.sound_bird_bt,
			"drawable://" + R.drawable.sound_natural_bt,
	};

	public static final String[] ServerAddress= new String[]{
			//회사서버 IP, 메인서버 IP
		"52.78.100.149","52.78.100.149"
	};

	public static final String[] PhP= new String[]{
			//8개
			"Examine_upload.php",
			"Examine_login.php",
			"Admin_login.php",
			"Admin_setting_checking.php",
			"Admin_setting_testset.php",
			"Admin_result_All.php",
			"Admin_result_Month.php",
			"Admin_result_Person.php"
	};


	private Constants() {
	}

	public static class Config {
		public static final boolean DEVELOPER_MODE = false;
	}
}
