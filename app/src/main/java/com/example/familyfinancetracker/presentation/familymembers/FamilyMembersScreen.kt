package com.example.familyfinancetracker.presentation.familymembers

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

import androidx.compose.runtime.LaunchedEffect

import com.example.familyfinancetracker.data.model.FamilyMember
import com.example.familyfinancetracker.data.remote.FirestoreSource

@Composable
fun FamilyMembersScreen() {

    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var relation by remember { mutableStateOf("") }
    var contribution by remember { mutableStateOf("") }

    var message by remember { mutableStateOf("") }

    var editingMember by remember {
        mutableStateOf<FamilyMember?>(null)
    }

    val familyMembers = remember {
        mutableStateListOf<FamilyMember>()
    }

    LaunchedEffect(Unit) {

        FirestoreSource.getFamilyMembers(

            onSuccess = { firestoreMembers ->

                familyMembers.clear()
                familyMembers.addAll(firestoreMembers)
            },

            onError = {
                println(it)
            }
        )
    }

    val totalContribution = familyMembers.sumOf {
        it.contribution.toIntOrNull() ?: 0
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(16.dp)
        ) {

        item {

            Text(
                text = "Family Members",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                },
                label = {
                    Text("Name")
                },
                modifier = Modifier.fillMaxWidth(0.9f)            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = age,
                onValueChange = {
                    age = it
                },
                label = {
                    Text("Age")
                },
                modifier = Modifier.fillMaxWidth(0.9f)            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = relation,
                onValueChange = {
                    relation = it
                },
                label = {
                    Text("Relation")
                },
                modifier = Modifier.fillMaxWidth(0.9f)            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contribution,
                onValueChange = {
                    contribution = it
                },
                label = {
                    Text("Contribution")
                },
                modifier = Modifier.fillMaxWidth(0.9f)            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {

                    if (
                        name.isBlank() ||
                        age.isBlank() ||
                        relation.isBlank() ||
                        contribution.isBlank()
                    ) {
                        message = "Please fill all fields"
                        return@Button
                    }

                    // UPDATE
                    if (editingMember != null) {

                        val updatedMember =
                            editingMember!!.copy(
                                name = name,
                                age = age,
                                relation = relation,
                                contribution = contribution
                            )

                        FirestoreSource.updateFamilyMember(
                            familyMember = updatedMember,

                            onSuccess = {

                                FirestoreSource.getFamilyMembers(

                                    onSuccess = { firestoreMembers ->

                                        familyMembers.clear()
                                        familyMembers.addAll(
                                            firestoreMembers
                                        )

                                        editingMember = null

                                        name = ""
                                        age = ""
                                        relation = ""
                                        contribution = ""

                                        message =
                                            "Member Updated Successfully"
                                    },

                                    onError = {
                                        message = it
                                    }
                                )
                            },

                            onError = {
                                message = it
                            }
                        )

                        return@Button
                    }

                    // ADD
                    val member = FamilyMember(
                        id = familyMembers.size + 1,
                        name = name,
                        age = age,
                        relation = relation,
                        contribution = contribution
                    )

                    FirestoreSource.addFamilyMember(
                        familyMember = member,

                        onSuccess = {

                            FirestoreSource.getFamilyMembers(

                                onSuccess = { firestoreMembers ->

                                    familyMembers.clear()
                                    familyMembers.addAll(
                                        firestoreMembers
                                    )

                                    name = ""
                                    age = ""
                                    relation = ""
                                    contribution = ""

                                    message =
                                        "Member Added Successfully"
                                },

                                onError = {
                                    message = it
                                }
                            )
                        },

                        onError = {
                            message = it
                        }
                    )
                }
            ) {

                Text(
                    if (editingMember == null)
                        "Add Member"
                    else
                        "Update Member"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (message.isNotEmpty()) {
                Text(message)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Card(
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("👨 Members")

                        Text(
                            text = "${familyMembers.size}",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

                Card(
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text("💰 Contribution")

                        Text(
                            text = "₹$totalContribution",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }


            if (familyMembers.isEmpty()) {

                item {

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),

                            horizontalAlignment =
                                Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = "👨‍👩‍👧‍👦 No Family Members Added",
                                style =
                                    MaterialTheme.typography.titleMedium
                            )

                            Spacer(
                                modifier = Modifier.height(8.dp)
                            )

                            Text(
                                text =
                                    "Add family members to manage finances together."
                            )
                        }
                    }
                }
            }

        items(familyMembers.reversed()) { member ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "👤 ${member.name}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "${member.relation} • ${member.age} Years"
                    )

                    Spacer(
                        modifier = Modifier.height(4.dp)
                    )

                    Text(
                        text = "Contribution: ₹${member.contribution}"
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )

                    Button(
                        onClick = {

                            if (
                                member.documentId.isBlank()
                            ) {
                                return@Button
                            }

                            FirestoreSource.deleteFamilyMember(
                                documentId =
                                    member.documentId,

                                onSuccess = {
                                    familyMembers.remove(
                                        member
                                    )
                                },

                                onError = {
                                    println(it)
                                }
                            )
                        }
                    ) {
                        Text("Delete")
                    }

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Button(
                        onClick = {

                            editingMember = member

                            name = member.name
                            age = member.age
                            relation = member.relation
                            contribution =
                                member.contribution

                            message =
                                "Editing Member..."
                        }
                    ) {
                        Text("Edit")
                    }
                }
            }
        }

        item {
            Spacer(
                modifier = Modifier.height(100.dp)
            )
        }
    }
}
}