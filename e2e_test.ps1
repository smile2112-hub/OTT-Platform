$base='http://127.0.0.1:8081'
$user = 'e2e_' + (Get-Date -Format yyyyMMddHHmmss)
$reg = @{ nameSurname='E2E Test'; username=$user; password='Test@123'; email=($user + '@example.com') } | ConvertTo-Json
Write-Host "Registering $user"
Invoke-RestMethod -Uri "$base/api/user/register" -Method Post -Body $reg -ContentType 'application/json' -ErrorAction Stop
Write-Host "Logging in"
$login = @{ username=$user; password='Test@123' } | ConvertTo-Json
$resp = Invoke-RestMethod -Uri "$base/api/login" -Method Post -Body $login -ContentType 'application/json' -ErrorAction Stop
$token = $resp.token
Write-Host "Token: $token"
Write-Host "Purchasing BASIC"
Invoke-RestMethod -Uri "$base/api/subscription/purchase/BASIC" -Method Post -Headers @{ Authorization = "Bearer $token" } -ErrorAction Stop
$sub = Invoke-RestMethod -Uri "$base/api/subscription/my" -Method Get -Headers @{ Authorization = "Bearer $token" } -ErrorAction Stop
Write-Host "Subscription ID: $($sub.id)"
Write-Host "Creating payment..."
$payment = Invoke-RestMethod -Uri "$base/api/payment/create/$($sub.id)" -Method Post -Headers @{ Authorization = "Bearer $token" } -ErrorAction Stop
Write-Host "Payment created:"; $payment | ConvertTo-Json -Depth 5
Write-Host "Verifying (dummy)..."
$v = Invoke-RestMethod -Uri "$base/api/payment/verify-any/dummy-id" -Method Put -Headers @{ Authorization = "Bearer $token" } -ErrorAction Stop
Write-Host "Verify response: $v"
Write-Host "Payments after verify:"
$list = Invoke-RestMethod -Uri "$base/api/payment/my" -Method Get -Headers @{ Authorization = "Bearer $token" } -ErrorAction Stop
$list | ConvertTo-Json -Depth 6
