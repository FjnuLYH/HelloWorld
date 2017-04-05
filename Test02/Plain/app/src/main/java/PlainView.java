import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Administrator on 2017/3/27.
 */

public class PlainView {

    public float currentX;
    public float currentY;

    Bitmap plane;

    public PlainView(Context context) {
        super();

        plane = BitmapFactory.decodeResource(context.getResources(),
                android.R.mipmap.sym_def_app_icon);//
        setFocusable(true);
    }

        @Override
        public void onDraw(Canvas canvas){
            super.onDraw(canvas);

            Paint paint = new Paint();

            canvas.drawBitmap( plane,currentX,currentY,paint);
        }

    }


}
