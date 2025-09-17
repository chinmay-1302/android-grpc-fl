package com.example.grpcdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.grpcdemo.ui.theme.GRPCDemoTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import io.grpc.ManagedChannelBuilder
import app.flotilla.EdgeServiceGrpc
import app.flotilla.EchoMessage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GRPCDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Connect to your server
                val channel = ManagedChannelBuilder
                    .forAddress("10.0.2.2", 50051) // if running server locally with Android Emulator
                    .usePlaintext()
                    .build()

                val stub = EdgeServiceGrpc.newBlockingStub(channel)

                // Build request
                val request = EchoMessage.newBuilder()
                    .setText("Hello, World")
                    .build()

                // Make RPC call
                val response = stub.echo(request)

                println("🔁 Server responded: ${response.text}")

                channel.shutdown()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GRPCDemoTheme {
        Greeting("Android")
    }
}