package com.example.familyfinancetracker.presentation.familymembers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.familyfinancetracker.data.model.FamilyMember

@Composable
fun FamilyMembersScreen() {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var contribution by remember { mutableStateOf("") }

    val members = remember {
        mutableStateListOf<FamilyMember>()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Family Members",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = age,
                onValueChange = { age = it },
                label = { Text("Age") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = relation,
                onValueChange = { relation = it },
                label = { Text("Relation") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = contribution,
                onValueChange = { contribution = it },
                label = { Text("Monthly Contribution") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    if (
                        name.isBlank() ||
                        age.isBlank() ||
                        relation.isBlank() ||
                        contribution.isBlank()
                    ) {
                        return@Button
                    }

                    members.add(
                        FamilyMember(
                            id = members.size + 1,
                            name = name,
                            age = age,
                            relation = relation,
                            contribution = contribution
                        )
                    )

                    name = ""
                    age = ""
                    relation = ""
                    contribution = ""
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Member")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val totalContribution = members.sumOf {
                it.contribution.toIntOrNull() ?: 0
            }

            Text(
                text = "Total Contribution: ₹$totalContribution",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (members.isNotEmpty()) {

                LazyColumn(
                    modifier = Modifier.height(250.dp)
                ) {

                    items(members) { member ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {

                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {

                                Text("Name: ${member.name}")
                                Text("Age: ${member.age}")
                                Text("Relation: ${member.relation}")
                                Text("Contribution: ₹${member.contribution}")

                                Spacer(modifier = Modifier.height(8.dp))

                                Row {

                                    TextButton(
                                        onClick = {
                                            members.remove(member)
                                        }
                                    ) {
                                        Text("Delete")
                                    }

                                    TextButton(
                                        onClick = {

                                            name = member.name
                                            age = member.age
                                            relation = member.relation
                                            contribution = member.contribution

                                            members.remove(member)
                                        }
                                    ) {
                                        Text("Edit")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}