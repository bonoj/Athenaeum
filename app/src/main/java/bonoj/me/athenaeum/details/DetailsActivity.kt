package bonoj.me.athenaeum.details

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bonoj.me.athenaeum.R
import bonoj.me.athenaeum.root.AthenaeumApplication

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        AthenaeumApplication.graph.inject(this)


    }
}
