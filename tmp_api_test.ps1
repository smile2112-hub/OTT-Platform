$base='http://127.0.0.1:8081'
$user='ui_test_user_'+(Get-Date -Format yyyyMMddHHmmss)
$reg=@{nameSurname='UI Test'; username=$user; password='Test@123'; email=($user+'@example.com')} | ConvertTo-Json
Write-Host 'Registering' $user
Invoke-RestMethod -Uri ($base + '/api/user/register') -Method Post -Body $reg -ContentType 'application/json'
$login=@{username=$user; password='Test@123'} | ConvertTo-Json
Write-Host 'Logging in'
$resp=Invoke-RestMethod -Uri ($base + '/api/login') -Method Post -Body $login -ContentType 'application/json'
$token=$resp.token
Write-Host 'Token:' $token
Write-Host 'Purchase subscription BASIC'
Invoke-RestMethod -Uri ($base + '/api/subscription/purchase/BASIC') -Method Post -Headers @{Authorization=('Bearer '+$token)}
Write-Host 'Get my subscription'
$sub=Invoke-RestMethod -Uri ($base + '/api/subscription/my') -Method Get -Headers @{Authorization=('Bearer '+$token)}
$sub | ConvertTo-Json -Depth 5
$subId=$sub.id
Write-Host 'Create payment for subscription id' $subId
$payment=Invoke-RestMethod -Uri ($base + '/api/payment/create/' + $subId) -Method Post -Headers @{Authorization=('Bearer '+$token)}
$payment | ConvertTo-Json -Depth 5
Write-Host 'List my payments'
Invoke-RestMethod -Uri ($base + '/api/payment/my') -Method Get -Headers @{Authorization=('Bearer '+$token)} | ConvertTo-Json -Depth 5
