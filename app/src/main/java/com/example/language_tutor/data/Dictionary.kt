import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.language_tutor.ui.theme.LanguageTutorTheme
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class Word(val english: String = "", val russian: String = "")

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LanguageTutorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    DictionaryScreen()
                }
            }
        }
    }
}

@Composable
fun DictionaryScreen() {
    var wordList by remember { mutableStateOf(listOf<Word>()) }

    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance().getReference("words")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val words = mutableListOf<Word>()
                for (wordSnapshot in snapshot.children) {
                    val word = wordSnapshot.getValue(Word::class.java)
                    if (word != null) {
                        words.add(word)
                    }
                }
                wordList = words
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Dictionary", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(wordList) { word ->
                WordCard(word)
            }
        }
    }
}

@Composable
fun WordCard(word: Word) {
    Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "English: ${word.english}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Russian: ${word.russian}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
