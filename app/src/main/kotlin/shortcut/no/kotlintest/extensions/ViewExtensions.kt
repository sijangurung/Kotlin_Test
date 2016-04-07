package shortcut.no.kotlintest.extensions

import android.content.Context
import android.view.View

/**
 * Created by Sijan Gurung on 31/03/16.
 * Shortcut AS
 * sijan.gurung@shortcut.no
 */

val View.ctx : Context
    get()=context
