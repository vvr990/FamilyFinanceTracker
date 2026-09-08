package com.example.familyfinancetracker.presentation.familymembers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import com.example.familyfinancetracker.data.model.FamilyMember

import androidx.compose.material3.TextButton


@Composable
fun FamilyMembersScreen() {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var contribution by remember { mutableStateOf("") }

    val members = remember {
        mutableStateListOf<FamilyMember>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Family Members",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = relation,
            onValueChange = { relation = it },
            label = { Text("Relation") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = contribution,
            onValueChange = { contribution = it },
            label = { Text("Monthly Contribution") }
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            }
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

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        LazyColumn {

            items(members) { member ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text("Name: ${member.name}")
                        Text("Age: ${member.age}")
                        Text("Relation: ${member.relation}")
                        Text("Contribution: ₹${member.contribution}")

                        Spacer(modifier = Modifier.height(8.dp))

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