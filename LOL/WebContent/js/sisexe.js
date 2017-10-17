function verf(str)
{
  var cmp = $("#pssw").val();

  if(str != cmp)
  {
    $("#cnf").attr("class", "alert alert-danger");
    $("#dng").attr("class", "form-group has-error has-feedback");
    $("#lbl").show();
  }
  else
  {
    $("#cnf").attr("class", "");
    $("#dng").attr("class", "form-group");
    $("#lbl").hide();
  }
}
