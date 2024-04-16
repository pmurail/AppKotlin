package fr.nextu.murail_pierre

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.util.Log
import java.net.URL

// TODO: Rename actions, choose action names that describe tasks that this
// IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
private const val ACTION_FOO = "fr.nextu.murail_pierre.action.FOO"
private const val ACTION_BAZ = "fr.nextu.murail_pierre.action.BAZ"

// TODO: Rename parameters
private const val EXTRA_PARAM1 = "fr.nextu.murail_pierre.extra.PARAM1"
private const val EXTRA_PARAM2 = "fr.nextu.murail_pierre.extra.PARAM2"

/**
 * An [IntentService] subclass for handling asynchronous task requests in
 * a service on a separate handler thread.

 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.

 */
class BeersService : IntentService("BeersService") {

    override fun onHandleIntent(intent: Intent?) {
        var url = URL("https://ror-next-u.onrender.com/beers.json")
        val co = url.openConnection()
        co.getInputStream()
        Log.d("com.nextu.murail_pierre"," BeersService")
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionFoo(param1: String?, param2: String?) {
        TODO("Handle action Foo")
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private fun handleActionBaz(param1: String?, param2: String?) {
        TODO("Handle action Baz")
    }

    companion object {
        /**
         * Starts this service to perform action Foo with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startActionFoo(context: Context, param1: String, param2: String) {
            val intent = Intent(context, BeersService::class.java).apply {
                action = ACTION_FOO
                putExtra(EXTRA_PARAM1, param1)
                putExtra(EXTRA_PARAM2, param2)
            }
            context.startService(intent)
        }

        /**
         * Starts this service to perform action Baz with the given parameters. If
         * the service is already performing a task this action will be queued.
         *
         * @see IntentService
         */
        // TODO: Customize helper method
        @JvmStatic
        fun startAction(context: Context) {
            val intent = Intent(context, BeersService::class.java)
            context.startService(intent)
        }
    }
}