package me.nxsyed.channelgroups

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.pubnub.api.PNConfiguration
import com.pubnub.api.PubNub
import com.pubnub.api.models.consumer.PNStatus
import com.pubnub.api.models.consumer.pubsub.PNMessageResult
import java.util.*
import com.pubnub.api.callbacks.PNCallback
import com.pubnub.api.callbacks.SubscribeCallback
import com.pubnub.api.models.consumer.channel_group.PNChannelGroupsAddChannelResult
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val subscribeText = findViewById<TextView>(R.id.textViewSubscribe)

        val pnConfiguration = PNConfiguration()
        pnConfiguration.subscribeKey = "sub-c-87dbd99c-e470-11e8-8d80-3ee0fe19ec50"
        pnConfiguration.publishKey = "pub-c-09557b6c-9513-400f-a915-658c0789e264"
        pnConfiguration.secretKey = "true"
        pnConfiguration.uuid = "Syed"
        val pubNub = PubNub(pnConfiguration)

        pubNub.addChannelsToChannelGroup()
                .channelGroup("ExampleGroup")
                .channels(Arrays.asList("ch1", "ch2"))
                .async(object: PNCallback<PNChannelGroupsAddChannelResult>(){
                    override fun onResponse(result: PNChannelGroupsAddChannelResult?, status: PNStatus){

                    }
                })


        pubNub.run {
            addListener(object : SubscribeCallback()  {
                override fun status(pubnub: PubNub, status: PNStatus) {

                }
                override fun message(pubnub: PubNub, message: PNMessageResult) {
                    println(message)
                    runOnUiThread {
                        subscribeText.text = "Channel: ${message.channel} \n Message: ${message.message.toString()}"
                    }
                }
                override fun presence(pubnub: PubNub, presence: PNPresenceEventResult) {
                }
            })
            subscribe()
                    .channels(Arrays.asList("whiteboard")) // subscribe to channels
                    .channelGroups(Arrays.asList("ExampleGroup"))
                    .execute()
        }

    }

}
